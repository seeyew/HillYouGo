package com.seeyewmo.hillyougo.service;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeyewmo.hillyougo.model.NYTResponse;
import com.seeyewmo.hillyougo.model.NYTWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by seeyew on 7/7/16.
 */
public class DataService {
    private String mSection;
    private int mPeriod;
    private Context mContext;

    private NYTWrapper memory = null;
    private ObjectMapper mapper = new ObjectMapper();
    File file;
    Observable<NYTWrapper> mMemoryCache;
    Observable<NYTWrapper> mDiskCache;
    Observable<NYTWrapper> mNetworkSource;

    public DataService(Context context, String section, int period) {
        mSection = section;
        mPeriod = period;
        mContext = context;
        file = new File(mContext.getCacheDir(), mSection);

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mMemoryCache = Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                subscriber.onNext(memory);
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).compose(logSource("MemoryCache"));

        mDiskCache = Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                NYTWrapper nytWrapper = getFromDiskCache(mSection);
                subscriber.onNext(nytWrapper);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .doOnNext(new Action1<NYTWrapper>() {
            @Override
            public void call(NYTWrapper nytWrapper) {
                updateMemoryCache(nytWrapper);
            }
        }).compose(logSource("DiskCache"));


        NYTService service = ServiceFactory.createRetrofitService(NYTService.class, NYTService.SERVICE_ENDPOINT);
        mNetworkSource = service.getArticles(mSection, mPeriod)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<NYTResponse, NYTWrapper>() {
                    @Override
                    public NYTWrapper call(NYTResponse nytResponse) {
                        Log.i("DataService", "Map is on this thread:" + Thread.currentThread().getName());
                        return new NYTWrapper(nytResponse);
                    }
                }).observeOn(Schedulers.io()).doOnNext(new Action1<NYTWrapper>() {
                    @Override
                    public void call(NYTWrapper nytWrapper) {
                        Log.i("DataService", "Do on next is on this thread:" + Thread.currentThread().getName());
                        updateMemoryCache(nytWrapper);
                        updateDiskCache(nytWrapper);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).compose(logSource("NetworkSource"));
    }

    public Observable<NYTWrapper> getArticles(boolean refresh) {
        if (refresh) {
            memory = null;
            file.delete();
        }

        return Observable
                .concat(mMemoryCache, mDiskCache, mNetworkSource)
                .takeFirst(new Func1<NYTWrapper, Boolean>() { //using takefirst to avoid exception
                    @Override
                    public Boolean call(NYTWrapper nytWrapper) {
                        return nytWrapper != null; //&& nytWrapper.isUpToDate();
                    }
                });
    }

    // Simple logging to let us know what each source is returning
    Observable.Transformer<NYTWrapper, NYTWrapper> logSource(final String source) {
        return new Observable.Transformer<NYTWrapper, NYTWrapper>() {
            @Override
            public Observable<NYTWrapper> call(Observable<NYTWrapper> observable) {
                return observable.doOnNext(new Action1<NYTWrapper>() {
                    @Override
                    public void call(NYTWrapper nytWrapper) {
                        if (nytWrapper == null) {
                            Log.i("DataService", source + " does not have any data.");
                        } else if (!nytWrapper.isUpToDate()) {
                            Log.i("DataService", source + " has stale data.");
                        } else {
                            Log.i("DataService", source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };
    }

    private void updateCache(NYTWrapper nytWrapper) {
        updateMemoryCache(nytWrapper);
        updateDiskCache(nytWrapper);
    }

    private void updateMemoryCache(NYTWrapper nytWrapper) {
        memory = nytWrapper;
    }

    private void updateDiskCache(NYTWrapper nytWrapper) {
        try {
            file.delete();
            String jsonInString = mapper.writeValueAsString(nytWrapper);
            Log.i("DataService", "JsonInString:" + jsonInString);
            mapper.writeValue(file, nytWrapper);
            Log.i("DataService", "writeDisk Succeeded!");
        } catch (IOException e) {
            Log.e("DataService", "writeDisk failed" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("DataService", "writeDisk failed" + e);
            e.printStackTrace();
        }
    }

    private NYTWrapper getMemoryCache() {
        return memory;
    }


    private NYTWrapper getFromDiskCache(String section) {
        try {

            if (!file.exists()) {
                Log.i("DataService", "File doesn't exists");
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
                Log.i("DataService", "String in file pre read: " + text.toString());
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
                Log.e("DataService", "stringbuilder" + e);
            }



            return mapper.readValue(file, NYTWrapper.class);
        } catch (IOException e) {
            Log.e("DataService", "Get from disk failed" + e);
            return null;
        }
    }
}
