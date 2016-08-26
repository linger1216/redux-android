package com.lid.redux.library;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lid.redux.library.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyou on 16/6/17.
 */

public class DefaultLogMiddleware implements Middleware {
    private List<Action> actions;
    private Context _context;

    public DefaultLogMiddleware(Context context) {
        actions = new ArrayList<>();
        _context = context;
    }

    @Override
    public void dispatch(final Store store, final Action action) {
        Log.d("redux", "--> " + action.toString());
        if (Redux.DEBUG) {
            actions.add(action);
        }
    }

    @Override
    public void destroy() {
        if (actions!=null && !actions.isEmpty()) {
            // save actions to local file or db or sth.
        }
    }
}