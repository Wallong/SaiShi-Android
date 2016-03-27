package com.twtstudio.coder.saishi_android.interactor;

import com.twtstudio.coder.saishi_android.ui.main.list.OnAddViewsCallback;
import com.twtstudio.coder.saishi_android.ui.main.list.OnGetDataItemsCallback;

/**
 * Created by clifton on 16-2-27.
 */
public interface DataInteractor {

    void getDataItems(String type, int page, OnGetDataItemsCallback onGetDataItemsCallback);

    void addViews(String id , OnAddViewsCallback onAddViewsCallback);

}
