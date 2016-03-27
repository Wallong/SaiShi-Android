package com.twtstudio.coder.saishi_android.ui;

import android.app.Activity;
import android.app.Fragment;

import java.util.List;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import dagger.ObjectGraph;

/**
 * Created by clifton on 16-2-27.
 */
public abstract class BaseFragment extends Fragment{

    private ObjectGraph mFragmentGraph;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentGraph = ((ContestApp) activity.getApplication()).createScopedGraph(getModules().toArray());
        mFragmentGraph.inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ApiClient.getInstance().cancelRequests(getActivity(), false);
    }

    protected abstract List<Object> getModules();
}
