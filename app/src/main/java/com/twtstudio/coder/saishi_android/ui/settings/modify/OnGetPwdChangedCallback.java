package com.twtstudio.coder.saishi_android.ui.settings.modify;

import com.twtstudio.coder.saishi_android.bean.NormalInfo;

/**
 * Created by clifton on 16-2-29.
 */
public interface OnGetPwdChangedCallback {

    void onSuccess(NormalInfo normalInfo);

    void onFailure(String errorString);
}
