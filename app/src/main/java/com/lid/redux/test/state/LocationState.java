package com.lid.redux.test.state;

import com.lid.redux.library.state.State;


/**
 * Created by tuyou on 16/6/17.
 */
public class LocationState extends State {

    private String content;

    public LocationState(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LocationState{" +
                "content=" + content +
                '}';
    }
}
