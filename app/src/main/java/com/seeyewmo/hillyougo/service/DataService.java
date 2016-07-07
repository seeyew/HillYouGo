package com.seeyewmo.hillyougo.service;

import android.util.Log;

import com.seeyewmo.hillyougo.model.NYTResponse;
import com.seeyewmo.hillyougo.model.NYTWrapper;

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

    private NYTWrapper memory = null;
    Observable<NYTWrapper> mMemoryCache;
    Observable<NYTWrapper> mNetworkSource;

    public DataService(String section, int period) {
        mSection = section;
        mPeriod = period;

        mMemoryCache = Observable.create(new Observable.OnSubscribe<NYTWrapper>() {
            @Override
            public void call(Subscriber<? super NYTWrapper> subscriber) {
                subscriber.onNext(memory);
                subscriber.onCompleted();
            }
        }).compose(logSource("MemoryCache"));

        NYTService service = ServiceFactory.createRetrofitService(NYTService.class, NYTService.SERVICE_ENDPOINT);
        mNetworkSource = service.getArticles(mSection, mPeriod)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<NYTResponse, NYTWrapper>() {
                    @Override
                    public NYTWrapper call(NYTResponse nytResponse) {
                        return new NYTWrapper(nytResponse);
                    }
                }).doOnNext(new Action1<NYTWrapper>() {
                    @Override
                    public void call(NYTWrapper nytWrapper) {
                        memory = nytWrapper;
                    }
                }).compose(logSource("NetworkSource"));
    }

    public Observable<NYTWrapper> getArticles(boolean refresh) {
        /*if (refresh) {
            memory = null;
        }*/

        return Observable
                .concat(mMemoryCache, mNetworkSource)
                .first(new Func1<NYTWrapper, Boolean>() {
                    @Override
                    public Boolean call(NYTWrapper nytWrapper) {
                        return nytWrapper != null && nytWrapper.isUpToDate();
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
                        }
                        else if (!nytWrapper.isUpToDate()) {
                            Log.i("DataService", source + " has stale data.");
                        }
                        else {
                            Log.i("DataService", source + " has the data you are looking for!");
                        }
                    }
                });
            }
        };


    }
}
