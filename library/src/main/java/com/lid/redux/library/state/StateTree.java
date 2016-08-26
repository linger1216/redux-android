package com.lid.redux.library.state;

import com.lid.redux.library.Redux;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lid on 16/8/13.
 */

public class StateTree {
    private Map<String, State> states = null;

    public StateTree() {
        states = new ConcurrentHashMap<>();
    }

    public StateTree put(State state) {
        states.put(state.getClass().getSimpleName(), state);
        return this;
    }

    public synchronized StateTree put(String name, State state) {
        states.put(name, state);
        return this;
    }

    @SuppressWarnings("unchecked")
    public synchronized <T extends State> T getState(String name) {
        return (T)states.get(Redux.getStateKey(name));
    }

    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        Collection<State> collection = states.values();
        for (State state : collection) {
            writer.println(state.toString());
        }
    }

    @Override
    public String toString() {
        return "StateTree{" + "states=" + states + '}';
    }
}

