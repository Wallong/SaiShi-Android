package com.twtstudio.coder.saishi_android.ui.main.list;

import java.util.List;

import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.bean.ImageInfo;

/**
 * Created by clifton on 16-2-27.
 */
public interface OnGetDataItemsCallback {

    void onSuccess(List<DataItem> datum, ImageInfo imageInfo);

    void onFailure(String errorString);
}
