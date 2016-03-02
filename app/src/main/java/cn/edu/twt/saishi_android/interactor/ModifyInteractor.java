package cn.edu.twt.saishi_android.interactor;

import cn.edu.twt.saishi_android.ui.settings.modify.OnGetPwdChangedCallback;

/**
 * Created by clifton on 16-2-29.
 */
public interface ModifyInteractor {

    void changePwd(String oldPwd, String newPwd, OnGetPwdChangedCallback onGetPwdChangedCallback);
}
