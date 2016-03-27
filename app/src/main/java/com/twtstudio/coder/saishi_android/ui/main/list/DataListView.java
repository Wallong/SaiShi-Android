package com.twtstudio.coder.saishi_android.ui.main.list;

import java.util.List;

import com.twtstudio.coder.saishi_android.bean.DataItem;

/**
 * Created by clifton on 16-2-27.
 */
public interface DataListView {
    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListData(List<DataItem> items);

    void addListData(List<DataItem> items);

    void startContentActivity(int position);

    void startLoginActivity();

    void showFooter();

    void hideFooter();

    void notifySomething();
}
