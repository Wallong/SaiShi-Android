package com.twtstudio.coder.saishi_android.interactor;

import com.twtstudio.coder.saishi_android.ui.register.register.OnRegisterCallback;

/**
 * Created by clifton on 16-4-4.
 */
public interface RegisterInteractor {

    void register(String username, String sex, String age, String pwd,
                  String danwei, String zhiwu, String phone, String verify, String cookie, OnRegisterCallback onRegisterCallback);
}
