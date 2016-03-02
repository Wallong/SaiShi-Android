package cn.edu.twt.saishi_android.ui.settings.modify;

import cn.edu.twt.saishi_android.bean.NormalInfo;

/**
 * Created by clifton on 16-2-29.
 */
public interface OnGetPwdChangedCallback {

    void onSuccess(NormalInfo normalInfo);

    void onFailure(String errorString);
}
