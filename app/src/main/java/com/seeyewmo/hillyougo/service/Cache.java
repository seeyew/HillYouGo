package com.seeyewmo.hillyougo.service;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeyewmo.hillyougo.model.NYTWrapper;
import com.seeyewmo.hillyougo.model.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by seeyew on 7/9/16.
 */
final class Cache {
    private static String TAG = "Cache";
    private static Cache sInstance;

    static synchronized Cache getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Cache(context.getApplicationContext());
        }
        return sInstance;
    }

    private final Map<String,PublishSubject<NYTWrapper>> mNewupdatesPublishSubject = new HashMap<>();
    private final Map<String, NYTWrapper> sectionToNYTArticlesList = new HashMap<>();;
    private Context mContext;
    private ObjectMapper mapper;
    private File mCacheDir;

    public Cache(Context context) {
        this.mContext = context;

        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
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
                return nytWrapper.getResults().get(position);
            }
        }
        return null;
    }

    public Observable<NYTWrapper> getArticles(String section) {
        Observable<NYTWrapper> memoryObservable = Observable.just(getFromInMemoryCache(section))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(applyLogging(section,"memory"));
        Observable<NYTWrapper> diskObservable = getFromDisk(section).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .compose(applyLogging(section, "disk"));
        Observable<NYTWrapper> localDataObservable = Observable.concat(memoryObservable,diskObservable)
                .firstOrDefault(null,new Func1<NYTWrapper, Boolean>() {
                    @Override
                    public Boolean call(NYTWrapper nytWrapper) {
                        return nytWrapper != null;
                    }
                });
        return localDataObservable.concatWith(getPublishSubjectForSection(section).asObservable().compose(applyLogging(section,"PublishSubject"))).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void cacheNewArticles(final String section,final NYTWrapper nytWrapper) {
        Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                //Cache the new data provided!
                Log.i(TAG, section + " caching data into memory and disk");
                cacheInMemory(section, nytWrapper);
                cacheOnDisk(section, nytWrapper);
                subscriber.onNext(nytWrapper);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NYTWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Caching new articles failed", e);
                    }

                    @Override
                    public void onNext(NYTWrapper nytWrapper) {
                        Log.i(TAG, section + " calling publishSubject.onNext()");
                        getPublishSubjectForSection(section).onNext(nytWrapper);
                    }
                });
    }

    private PublishSubject<NYTWrapper> getPublishSubjectForSection(String section) {
        PublishSubject<NYTWrapper> publishSubject = mNewupdatesPublishSubject.get(section);
        if (publishSubject == null) {
            publishSubject = PublishSubject.create();//.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            mNewupdatesPublishSubject.put(section, publishSubject);
        }
        return publishSubject;
    }

    private NYTWrapper getFromInMemoryCache(String section) {
        synchronized (sectionToNYTArticlesList) {
            return sectionToNYTArticlesList.get(section);
        }
    }

    private void cacheInMemory(String section, NYTWrapper nytWrapper) {
        synchronized (sectionToNYTArticlesList) {
            sectionToNYTArticlesList.put(section, nytWrapper);
        }
    }

    private Observable<NYTWrapper> getFromDisk(final String section) {
        return Observable.just(readFromDiskInner(section)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<NYTWrapper>() {
                    @Override
                    public void call(NYTWrapper nytWrapper) {
                        if (nytWrapper != null) {
                            Log.i(TAG, section + " cache disk data to memory");
                            cacheInMemory(section, nytWrapper);
                        }
                    }
                });
    }

    private NYTWrapper readFromDiskInner(String section) {
        try {
            File file = new File(mCacheDir, section);
            if (!file.exists()) {
                return null;
            }
            //Read text from file
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

            return mapper.readValue(file, NYTWrapper.class);
        } catch (IOException e) {
            return null;
        }
    }

    private void cacheOnDisk(String section, NYTWrapper nytWrapper) {
        try {
            File file = new File(mCacheDir, section);
            file.delete();
            String jsonInString = mapper.writeValueAsString(nytWrapper);
            mapper.writeValue(file, nytWrapper);
        } catch (IOException e) {
            Log.e(TAG, "CacheOnDisk failed",e);
        }
    }

    // Simple logging to let us know what each source is returning
    private Observable.Transformer<NYTWrapper, NYTWrapper> applyLogging(final String section, final String source) {
        return new Observable.Transformer<NYTWrapper, NYTWrapper>() {
            @Override
            public Observable<NYTWrapper> call(Observable<NYTWrapper> nytWrapperObservable) {
                return nytWrapperObservable.doOnNext(new Action1<NYTWrapper>() {
                    @Override
                    public void call(NYTWrapper nytWrapper) {
                        Log.i(TAG, section +" Emitting from:" + source);
                        if (nytWrapper == null) {
                            Log.i(TAG, section +":"+ source + " does not have any data.");
                        } else if (!nytWrapper.isUpToDate()) {
                            Log.i(TAG, section +":"+ source  + " has stale data.");
                        } else {
                            Log.i(TAG, section +":"+ source + " has the data you are looking for!");
                        }

                    }
                });
            }
        };

    }
}
