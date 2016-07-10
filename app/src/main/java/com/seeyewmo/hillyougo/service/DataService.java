package com.seeyewmo.hillyougo.service;

import android.content.Context;
import android.util.Log;

import com.seeyewmo.hillyougo.model.NYTResponse;
import com.seeyewmo.hillyougo.model.NYTWrapper;
import com.seeyewmo.hillyougo.model.Result;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by seeyew on 7/9/16.
 */
public class DataService {
    private static String TAG = "DataService";
    private static DataService sInstance;

    public static synchronized DataService getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DataService(context.getApplicationContext());
        }
        return sInstance;
    }
    private Context mContext;
    private Cache mCache;
    final PublishSubject<NYTWrapper> publishCacheOrWaitPublish = PublishSubject.create();
    private NYTService service = ServiceFactory.createRetrofitService(NYTService.class,
            NYTService.SERVICE_ENDPOINT);

    private DataService(Context context) {
        this.mContext = context;
        this.mCache = Cache.getInstance(context);
    }

    public Result getOneArticle(final String section, int position) {
        return mCache.getOneArticle(section, position);
    }

    public Observable<NYTWrapper> getArticles(final String section) {
        //TODO:
        //How can client know if the result means
        // 1) no articles" from all sources --> show "no news", or
        // 2) No cached articles, but a download has been launched --> Show "download in progress"
        //Potential solution  (A) could mean null NYTWrapper means we don't have anything, where as
        //NYTWrapper with no results means download completed but we have nothing.
        //Potential solution (B) if there's local data return regardless and launch refresh if necessary
        //AND if there's no local data, wait to get data before returning.

        //Original implementation with problems described above
//        return mCache.getArticles(section).doOnNext(new Action1<NYTWrapper>() {
//            @Override
//            public void call(NYTWrapper nytWrapper) {
//                if (nytWrapper == null || !nytWrapper.isUpToDate()) {
//                    Log.i(TAG, section + " is stale, request refresh");
//                    requestRefresh(section);
//                }
//            }
//        });


        //What has to happen?
        //return values if we have it
        //if the value is out of date, get from server
        //if we don't have any values, get from server
        //if server returns error, let the client of this method know.
        //publishCacheOrWaitPublish subscribes to observable from local storage
        mCache.getArticles(section).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {
                //should never complete!
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Reading from Cache failed " + e);
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                //Get a refresh if we have no data or if the data is still
                if (nytWrapper == null || !nytWrapper.isUpToDate()) {
                    Log.i(TAG, section + " cached data is " + nytWrapper == null? "null" : "still");
                    requestRefresh(section);
                }

                if (nytWrapper != null) {
                    Log.i(TAG, section + " cached data is available, up-to-date: " + (nytWrapper.isUpToDate()? "yes" : "no"));
                    //Found a hit locally, so let's notify our subscribers!
                    publishCacheOrWaitPublish.onNext(nytWrapper);
                }
                //we do not return if there's no data locally, we'll wait from the download.
            }
        });

        return publishCacheOrWaitPublish.asObservable();
    }

    public Observable<Void> requestRefresh(final String section) {
        final BehaviorSubject<Void> requestSubject = BehaviorSubject.create();
        Log.i(TAG, section + " is being refreshed");
        service.getArticles(section,7).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).map(new Func1<NYTResponse, NYTWrapper>() {
            @Override
            public NYTWrapper call(NYTResponse nytResponse) {
                return new NYTWrapper(nytResponse);
            }
        }).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {
                requestSubject.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "APIs to NYT failed " + e);
                RetrofitException error = (RetrofitException) e;
                //TODO: How to we tell clients?
                //requestSubject.onNext(null);
                requestSubject.onError(e);
                //TODO: convert it to another Throwable with messages that more client friendly?
                //LoginErrorResponse response = error.getBodyAs(LoginErrorResponse.class);
                publishCacheOrWaitPublish.onError(e);
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                Log.i(TAG, section + " refresh request returned");
                mCache.cacheNewArticles(section,nytWrapper);
                requestSubject.onNext(null);
            }
        });
        return requestSubject.asObservable();
    }
}
