package com.lid.redux.library.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lid on 16/7/15.
 */
public class RxUtils {

    static public Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);
    }

    static public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {
        private final int maxRetries;
        private final int retryDelaySeconds;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelaySeconds) {
            this.maxRetries = maxRetries;
            this.retryDelaySeconds = retryDelaySeconds;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                L.i("get error, it will try after " + retryDelaySeconds
                                        + " second, retry count " + retryCount);
                                return Observable.timer(retryDelaySeconds,
                                        TimeUnit.SECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }
}
