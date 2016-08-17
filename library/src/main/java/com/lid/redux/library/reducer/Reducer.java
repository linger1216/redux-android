package com.lid.redux.library.reducer;

import com.lid.redux.library.action.Action;
import com.lid.redux.library.state.State;
import com.lid.redux.library.state.StateTree;
import com.lid.redux.library.utils.Tuple2;

/**
 * Created by lid on 16/6/14.
 */
public interface Reducer {
    // reducer 一定是一个函数, 他负责状态的改变
    // 一个状态 + 动作 = 新状态 append action
    Tuple2<State, Action> call(Action action, StateTree state);
}
