package com.lid.redux.test;


import android.app.Application;

import com.lid.redux.library.DefaultLogMiddleware;
import com.lid.redux.library.Redux;
import com.lid.redux.library.Store;
import com.lid.redux.library.state.StateTree;
import com.lid.redux.test.state.LocationState;
import com.lid.redux.test.state.ProgressState;

import org.xutils.x;

public class App extends Application {

    public static Store store;

	@Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        Redux.init(getPackageName() + ".reducer", true);
        store = new Store(
                new StateTree().put(new ProgressState(0)).put(new LocationState("")),
                new DefaultLogMiddleware(getApplicationContext()), new AirMiddleware());

    }

}