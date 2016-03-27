package com.twtstudio.coder.saishi_android.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.support.StatusBarHelper;
import dagger.ObjectGraph;

/**
 * Created by clifton on 16-2-20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGraph = ((ContestApp)getApplication()).createScopedGraph(getModules().toArray());
        mActivityGraph.inject(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityGraph = null;
//        ContestApp.getRefWatcher().watch(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract List<Object> getModules();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarHelper.setStatusBar(this);
    }
}
