package com.lid.redux.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.lid.redux.library.Redux;
import com.lid.redux.library.action.Action;
import com.lid.redux.library.utils.L;
import com.lid.redux.library.utils.RxBus;
import com.lid.redux.library.utils.Tuple2;
import com.lid.redux.test.state.LocationState;
import com.lid.redux.test.state.ProgressState;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {

    private CompositeSubscription compositeSubscription;
    private ProgressBar progressBar;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RxView.clicks(findViewById(R.id.add))
                .throttleFirst(10, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        for (int i=0;i<100;i++) {
                            App.store.dispatch(Action.makeSyncRequestAction(i+"",Define.ACTION_PROGRESS_NAME).put("add", true));
                        }
                    }
                }));

        compositeSubscription.add(RxView.clicks(findViewById(R.id.sub))
                .throttleFirst(10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        App.store.dispatch(Action.makeSyncRequestAction(Define.ACTION_PROGRESS_NAME).put("add", false));
                    }
                }));

        compositeSubscription.add(RxView.clicks(findViewById(R.id.query))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
//                        for (int i=0;i<100;i++) {
                            App.store.dispatch(Action.makeAsyncRequestAction(Define.ACTION_LOCATION_NAME).put("city", "上海市"));
//                        }
                    }
                }));

        compositeSubscription.add(RxBus.getDefault().toObserverable(Tuple2.class)
                .subscribeOn(Schedulers.io())
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Tuple2>() {
                    @Override
                    public void call(Tuple2 tuple2) {
                        Action action = (Action)tuple2._2;
                        if (action.isResponse() && action.getCode() == 0) {
                            if (action.getName().equals(Define.ACTION_PROGRESS_NAME)) {
                                ProgressState progressState = (ProgressState)tuple2._1;
                                //L.i("redux", "<-- " + action.toString() + " -- " + progressState.getProgress());
                                progressBar.setProgress(progressState.getProgress());
                            } else if (action.getName().equals(Define.ACTION_LOCATION_NAME)) {
                                LocationState locationState = (LocationState)tuple2._1;
                                resultTextView.setText(locationState.getContent());
                                L.i("redux", "<-- " + action.toString());
                            }
                        }
                    }
                }));

        progressBar = (ProgressBar)findViewById(R.id.progress);
        ProgressState progressState = App.store.getState().getState(Redux.getStateKey(Define.ACTION_PROGRESS_NAME));
        progressBar.setProgress(progressState.getProgress());

        compositeSubscription.add(RxView.clicks(progressBar)
                .throttleFirst(10, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        finish();
                    }
                }));

        resultTextView = (TextView)findViewById(R.id.result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
        App.store.destroy();
    }
}
