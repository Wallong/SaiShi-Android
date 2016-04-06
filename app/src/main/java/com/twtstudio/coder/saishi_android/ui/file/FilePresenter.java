package com.twtstudio.coder.saishi_android.ui.file;

import android.view.View;

import java.io.File;

/**
 * Created by clifton on 16-2-28.
 */
public interface FilePresenter {
    void loadFileItems();

    void loadMoreFileItems();

    void firstTimeLoadExploreItems();

    void onItemClicked(View v , int position);

}
