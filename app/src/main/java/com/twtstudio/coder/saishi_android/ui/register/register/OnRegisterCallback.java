package com.twtstudio.coder.saishi_android.ui.register.register;

import com.twtstudio.coder.saishi_android.bean.NormalInfo;

/**
 * Created by clifton on 16-4-4.
 */
public interface OnRegisterCallback {

    void onSuccess(NormalInfo normalInfo);

    void onFailure(String errorString);
}
