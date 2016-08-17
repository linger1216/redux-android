package com.lid.redux.test;

import com.lid.redux.library.Middleware;
import com.lid.redux.library.Store;
import com.lid.redux.library.action.Action;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 *
 * Created by lid on 16/6/17.
 */

public class AirMiddleware implements Middleware {
    @Override
    public void dispatch(final Store store, final Action action) {
        Observable.just(action).filter(new Func1<Action, Boolean>() {
            @Override
            public Boolean call(Action action) {
                return action != null && action.isRequest() && action.isAsync() && action.getName().equals(Define.ACTION_LOCATION_NAME);
            }
        }).flatMap(new Func1<Action, Observable<String>>() {
            @Override
            public Observable<String> call(Action action) {
                String city = action.get("city");
                return request(city);
            }
        }).observeOn(Schedulers.io()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                store.dispatch(Action.makeSyncRequestAction(action.getId(),
                        action.getName()).put("content", s));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                store.dispatch(Action.makeSyncRequestAction(action.getId(),
                        action.getName()).setCode(-1).put("content", ""));
            }
        });
    }

    @Override
    public void dump() {

    }

    private Observable<String> request(final String city) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                RequestParams params = new RequestParams("http://gc.ditu.aliyun.com/geocoding?");
                params.addQueryStringParameter("a", city);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        subscriber.onNext(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        subscriber.onError(ex);
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        subscriber.onError(cex);
                    }

                    @Override
                    public void onFinished() {
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}




