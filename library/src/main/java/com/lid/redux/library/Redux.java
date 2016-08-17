package com.lid.redux.library;

/**
 * Created by lid on 16/8/15.
 */
public class Redux {

    static public boolean DEBUG;
    static public String REDUCER_PACKAGE_NAME;

    static public void init(String reducerPackageName){
        init(reducerPackageName, false);
    }

    static public void init(String reducerPackageName, boolean debug){
        REDUCER_PACKAGE_NAME = reducerPackageName;
        DEBUG = debug;
    }

    static public String getReducerClassName(String name) {
        return REDUCER_PACKAGE_NAME + "." + getReducerKey(name);
    }

    static public String getReducerKey(String name) {
        return name + "Reducer";
    }

    static public String getStateKey(String name) {
        return name + "State";
    }
}
