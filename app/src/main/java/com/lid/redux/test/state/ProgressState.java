package com.lid.redux.test.state;

import com.lid.redux.library.state.State;

/**
 * Created by tuyou on 16/6/17.
 */
public class ProgressState extends State {

    private int progress;

    public ProgressState(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ProgressState{" +
                "progress=" + progress +
                '}';
    }
}
