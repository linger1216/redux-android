package com.lid.redux.test.reducer;

import com.lid.redux.library.Redux;
import com.lid.redux.library.action.Action;
import com.lid.redux.library.reducer.Reducer;
import com.lid.redux.library.state.State;
import com.lid.redux.library.state.StateTree;
import com.lid.redux.library.utils.Tuple2;
import com.lid.redux.test.Define;
import com.lid.redux.test.state.ProgressState;

/**
 * Created by tuyou on 16/6/17.
 */
public class ProgressReducer implements Reducer {
    @Override
    public Tuple2<State, Action> call(Action action, StateTree state) {
        if (action.isSync() && action.isRequest() && action.getName().equals(Define.ACTION_PROGRESS_NAME)) {
            ProgressState progressState = state.getState(Redux.getStateKey(action.getName()));
            boolean isAdd = action.get("add");
            int level = isAdd ? progressState.getProgress() + 1 : progressState.getProgress() - 1;
            if (level >= 100) {
                level = 100;
            }
            if (level <= 0) {
                level = 0;
            }
            progressState.setProgress(level);
            return new Tuple2<>(progressState.clone(), Action.makeResponseAction(action,"success", 0));
        }
        return null;
    }
}
