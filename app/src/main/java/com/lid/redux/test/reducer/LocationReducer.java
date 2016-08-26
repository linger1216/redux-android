package com.lid.redux.test.reducer;

/**
 * Created by tuyou on 16/6/17.
 */

import com.lid.redux.library.Redux;
import com.lid.redux.library.action.Action;
import com.lid.redux.library.reducer.Reducer;
import com.lid.redux.library.state.State;
import com.lid.redux.library.state.StateTree;
import com.lid.redux.library.utils.Tuple2;
import com.lid.redux.test.Define;
import com.lid.redux.test.state.LocationState;

public class LocationReducer implements Reducer {
    @Override
    public Tuple2<State, Action> call(Action action, final StateTree state) {
        if (action.isSync() && action.isRequest() && action.getName().equals(Define.ACTION_LOCATION_NAME)){
            LocationState locationState =  state.getState(action.getName());
            if (action.getCode() >= 0) {
                String content = action.get("content");
                locationState.setContent(content);
                Action.makeResponseAction(action, true, 0, "success");
            } else {
                Action.makeResponseAction(action, false, action.getCode(), "failed");
            }
            return new Tuple2<>(locationState.clone(), action);
        }
        return null;
    }

}
