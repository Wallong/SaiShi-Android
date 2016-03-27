package com.twtstudio.coder.saishi_android.ui.settings;

import com.twtstudio.coder.saishi_android.bean.UpdateInfo;

/**
 * Created by clifton on 16-3-17.
 */
public interface OnUpdateCallback {

    void onSuccess(UpdateInfo updateInfo);

    void onFailure(String errorString);
}
