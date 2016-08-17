package com.lid.redux.library.state;

/**
 * Created by tuyou on 16/6/14.
 */
abstract public class State implements Cloneable{
    public State clone() {
        State o = null;
        try {
            o = (State) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
