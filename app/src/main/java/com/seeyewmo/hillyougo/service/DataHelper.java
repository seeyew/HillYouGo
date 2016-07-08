package com.seeyewmo.hillyougo.service;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeyewmo.hillyougo.model.NYTResponse;
import com.seeyewmo.hillyougo.model.NYTWrapper;
import com.seeyewmo.hillyougo.model.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by seeyew on 7/7/16.
 */
public class DataHelper {

    private static DataHelper sInstance;

    public static synchronized DataHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DataHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private Context mContext;
    private ObjectMapper mapper;
    private File mCacheDir;
    private final Map<String, NYTWrapper> sectionToNYTArticlesList;
    private Map<String, Set<Subscriber<? super NYTWrapper>>> mSubscribers;
    private NYTService service = ServiceFactory.createRetrofitService(NYTService.class,
            NYTService.SERVICE_ENDPOINT);

    private DataHelper(Context context) {
        this.mContext = context;
        sectionToNYTArticlesList = new HashMap<>();
        mSubscribers = new HashMap<>();

        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mCacheDir = context.getCacheDir();
    }

    public Result getOneArticle(final String section, int position) {
        synchronized (sectionToNYTArticlesList) {
            NYTWrapper nytWrapper = sectionToNYTArticlesList.get(section);
            if (nytWrapper != null && nytWrapper.getResults() != null && nytWrapper.getResults().size() > position) {
                Log.i("DataHelper", "Returning one article");
                return nytWrapper.getResults().get(position);
            }
        }
        return null;
    }

    public Observable<NYTWrapper> getArticles(final String section, boolean refresh) {
        return Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                Log.i("DataHelper", "Getting articles for section:" + section);
                Set<Subscriber<? super NYTWrapper>> set = mSubscribers.get(section);
                if (set == null) {
                    set = new HashSet<Subscriber<? super NYTWrapper>>();
                    mSubscribers.put(section, set);
                }
                set.add(subscriber);
                NYTWrapper nytWrapper = getCachedNYTWrapperRefreshIfNecessary(section);
                onNextForAllSubscribers(section, nytWrapper);
            }
        });
    }

    private void onNextForAllSubscribers(String section, NYTWrapper result) {
        for (Subscriber<? super NYTWrapper> subscriber : mSubscribers.get(section)) {
            subscriber.onNext(result);
        }
    }

    private NYTWrapper getCachedNYTWrapperRefreshIfNecessary(final String section) {
        NYTWrapper result = null;
        synchronized (sectionToNYTArticlesList) {
            //Step 1: in memory cache
            result = sectionToNYTArticlesList.get(section);
            Log.i("DataHelper", "Get From In Memory for section:" + section);
            if (result == null) {
                //Step 2: read from disk
                //Reading from disk might take a while, so it's handle in the Schedulers.io()
                //thread. Result will be updated when reading from disk completes.
                readFromDisk(section);
            } else if (!result.isUpToDate()) {
                //Step 3: Download if not up to date
                downloadFromNYT(section);
            }
        }
        //always return result!
        return result;
    }

    private void cacheInMemory(String section, NYTWrapper nytWrapper) {
        Log.i("DataHelper", "Cache In Memory");
        synchronized (sectionToNYTArticlesList) {
            sectionToNYTArticlesList.put(section, nytWrapper);
        }
    }

    private void cacheOnDisk(String section, NYTWrapper nytWrapper) {
        Log.i("DataHelper", "Cache on Disk");
        try {
            File file = new File(mCacheDir, section);
            file.delete();
            String jsonInString = mapper.writeValueAsString(nytWrapper);
            Log.i("DataHelper", "JsonInString:" + jsonInString);
            mapper.writeValue(file, nytWrapper);
            Log.i("DataHelper", "writeDisk Succeeded!");
        } catch (IOException e) {
            Log.e("DataHelper", "writeDisk failed" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("DataHelper", "writeDisk failed" + e);
            e.printStackTrace();
        }
    }

    private void readFromDisk(final String section) {
        Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                subscriber.onNext(readFromDiskInner(section));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                //do nothing
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                //Always put disk cache in memory
                cacheInMemory(section, nytWrapper);
                onNextForAllSubscribers(section, nytWrapper);
                //download if not up to date
                if (nytWrapper == null || !nytWrapper.isUpToDate()) {
                    downloadFromNYT(section);
                }
            }
        });

    }

    private NYTWrapper readFromDiskInner(String section) {
        try {
            File file = new File(mCacheDir, section);
            if (!file.exists()) {
                Log.i("DataHelper", "File doesn't exists");
                return null;
            }
            //Read text from file
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                Log.i("DataHelper", "String in file pre read: " + text.toString());
            } catch (IOException e) {
                //You'll need to add proper error handling here
                Log.e("DataHelper", "stringbuilder" + e);
            }

            return mapper.readValue(file, NYTWrapper.class);
        } catch (IOException e) {
            Log.e("DataHelper", "Get from disk failed" + e);
            return null;
        }
    }

    /**
     * Async download, will cache in memory and on disk when done.
     * Always call onNext for all subscribers if there's data available
     *
     * @param section
     */
    private void downloadFromNYT(final String section) {
        Log.i("DataHelper", "Downloading from NYT on thread:" + Thread.currentThread().getName());
        service.getArticles(section, 7)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<NYTResponse, NYTWrapper>() {
                    @Override
                    public NYTWrapper call(NYTResponse nytResponse) {
                        Log.i("DataService", "Map is on this thread:" + Thread.currentThread().getName());
                        return new NYTWrapper(nytResponse);
                    }
                }).observeOn(Schedulers.io()).doOnNext(new Action1<NYTWrapper>() {
            @Override
            public void call(NYTWrapper nytWrapper) {
                Log.i("DataHelper", "Downloading from NYT complete");
                //store in memory
                cacheInMemory(section, nytWrapper);
                //store on disk
                cacheOnDisk(section, nytWrapper);

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("DataHelper", "Error in network call " + e);
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                Log.i("DataHelper", "Download complete calling onNext()");
                //Notify
                onNextForAllSubscribers(section, nytWrapper);
            }
        });
    }

}
