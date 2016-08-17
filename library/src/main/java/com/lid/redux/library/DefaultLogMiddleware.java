package com.lid.redux.library;

import android.content.Context;

import com.google.gson.Gson;
import com.lid.redux.library.action.Action;
import com.lid.redux.library.utils.ACache;
import com.lid.redux.library.utils.L;

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
        L.i("redux", "--> " + action.toString());
        if (Redux.DEBUG) {
            actions.add(action);
        }
    }

    @Override
    public void dump() {
        if (actions!=null && !actions.isEmpty()) {
            ACache.get(_context).put("actions", new Gson().toJson(actions));
        }
    }
}