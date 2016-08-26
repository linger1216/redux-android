package com.lid.redux.library;

import com.lid.redux.library.action.Action;

/**
 * Created by tuyou on 16/6/15.
 */

public interface Middleware {
    void dispatch(Store store, Action action);
    void destroy();
}
