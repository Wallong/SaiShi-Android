package com.twtstudio.coder.saishi_android.ui.main.list;

import android.view.View;

/**
 * Created by clifton on 16-2-14.
 */
public interface DataPresenter {
    
    void loadDataItems(String type);

    void loadMoreDataItems(String type);

    void firstTimeLoadExploreItems(String type);

    void onItemClicked(View v , int position);

    void addViews(String id);

}
