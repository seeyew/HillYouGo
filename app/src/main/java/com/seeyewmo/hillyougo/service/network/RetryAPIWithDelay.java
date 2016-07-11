package com.seeyewmo.hillyougo.service.network;

import com.seeyewmo.hillyougo.service.network.RetrofitException;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by seeyew on 7/10/16.
 */
public class RetryAPIWithDelay implements
        Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;
    private int exponentialBackOffExponent;

    public RetryAPIWithDelay(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
        this.exponentialBackOffExponent = 2;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        RetrofitException exception = (RetrofitException) throwable;
                        //Only retry if it's a netowrk error
                        if (++retryCount < maxRetries
                                && exception.getKind() == RetrofitException.Kind.NETWORK) {
                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed).
                            // return Observable.timer(Math.round(Math.pow(retryDelayMillis, exponentialBackOffExponent)),
                            //        TimeUnit.MILLISECONDS);
                            return Observable.timer(retryDelayMillis * retryCount, TimeUnit.MILLISECONDS);
                        }

                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}
