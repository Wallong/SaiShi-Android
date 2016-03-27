package com.twtstudio.coder.saishi_android.interactor;

import com.twtstudio.coder.saishi_android.ui.login.OnLoginCallback;

/**
 * Created by clifton on 16-2-21.
 */
public interface LoginInteractor {

    void login(String username, String password, OnLoginCallback onLoginCallback);
}
