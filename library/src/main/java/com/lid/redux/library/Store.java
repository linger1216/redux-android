package com.lid.redux.library;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.lid.redux.library.action.Action;
import com.lid.redux.library.reducer.Reducer;
import com.lid.redux.library.state.State;
import com.lid.redux.library.state.StateTree;
import com.lid.redux.library.utils.Assert;
import com.lid.redux.library.utils.L;
import com.lid.redux.library.utils.RxBus;
import com.lid.redux.library.utils.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by tuyou on 16/6/15.
 */
public class Store implements Handler.Callback {

    private class ReducerMiddleware implements Middleware {
        @Override
        public void dispatch(Store store, Action action) {
            _dispatch(action);
        }

        @Override
        public void dump() {

        }
    }

    private StateTree stateTree;
    private List<Middleware> middleWares;
    private Map<String, Reducer> reducerMap;

    private HandlerThread handlerThread;
    private Handler handler;
    private ReducerMiddleware reducerMiddleware;

    public Store(StateTree initState, Middleware... middlewares) {
        this.reducerMap = new HashMap<>();
        this.middleWares = new ArrayList<>();
        this.stateTree = initState;

        reducerMiddleware = new ReducerMiddleware();
        for (int i = 0; i < middlewares.length; i++) {
            middleWares.add(middlewares[i]);
        }
        middleWares.add(reducerMiddleware);

        handlerThread = new HandlerThread("com.lid.redux.library");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), this);
        L.i("redux", "create store done");
    }

    public void destroy() {
        Observable.just("dump")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (Redux.DEBUG) {
                            for (Middleware i:middleWares) {
                                i.dump();
                                L.i("redux", i.getClass().getSimpleName() + " dump finish");
                            }
                        }
                        handler.removeCallbacksAndMessages(null);
                        handlerThread.quit();
                        handler = null;
                        L.i("redux", "store destroy");
                    }
                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        for (Middleware i : middleWares) {
            i.dispatch(this, (Action) msg.obj);
        }
        return true;
    }

    public void dispatch(Action action) {
        handler.sendMessage(handler.obtainMessage(1, action));
    }

    public StateTree getState() {
        return stateTree;
    }

    // private area
    @SuppressWarnings("unchecked")
    private void _dispatch(final Action action) {
        if (!TextUtils.isEmpty(action.getName())) {
            Reducer reducer = _getReducer(action);
            State state = _getState(action);

            if (reducer == null) {
                String reducerClassName = Redux.getReducerClassName(action.getName());
                if (TextUtils.isEmpty(reducerClassName)) {
                    L.e("redux", reducerClassName + " not found");
                }
            }

            if (state == null) {
                L.e("redux", "do you forgot create state in state tree?");
            }

            if (reducer != null && state != null) {
                Tuple2<State, Action> response = reducer.call(action, stateTree);
                RxBus.getDefault().post(response);
            }
        } else {
            L.e("redux", "do you forgot set action name ?");
        }
    }

    private Reducer _getReducer(Action action) {
        Reducer ret = __getReducerFromCache(action);
        return ret == null ? __createReducer(action) : ret;
    }

    private Reducer __getReducerFromCache(Action action) {
        Assert.notNull(action);
        Assert.notNull(reducerMap);
        return reducerMap.get(action.getName());
    }

    @SuppressWarnings("unchecked")
    private Reducer __createReducer(Action action) {
        Reducer reducer = null;
        try {
            Class reducerClass = Class.forName(Redux.getReducerClassName(action.getName()));
            reducer = (Reducer) reducerClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.toString());
        }
        if (reducer != null) {
            reducerMap.put(Redux.getReducerKey(action.getName()), reducer);
        }
        return reducer;
    }

    @SuppressWarnings("unchecked")
    private <S extends State> S _getState(Action action) {
        return (S) stateTree.getState(Redux.getStateKey(action.getName()));
    }


}



