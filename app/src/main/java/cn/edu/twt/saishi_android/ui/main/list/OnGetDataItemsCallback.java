package cn.edu.twt.saishi_android.ui.main.list;

import java.util.List;

import cn.edu.twt.saishi_android.bean.DataItem;

/**
 * Created by clifton on 16-2-27.
 */
public interface OnGetDataItemsCallback {

    void onSuccess(List<DataItem> datum);

    void onFailure(String errorString);
}
