package cn.edu.twt.saishi_android.ui.login;

import cn.edu.twt.saishi_android.bean.UserInfo;

/**
 * Created by clifton on 16-2-19.
 */
public interface OnLoginCallback {

    void onSuccess(UserInfo userInfo);

    void onFailure(String errorString);
}
