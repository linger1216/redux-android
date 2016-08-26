package com.lid.redux.library.utils;

import rx.Subscription;

/**
 * Created by lid on 16/7/15.
 */
public class RxUtils {
    static public void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
