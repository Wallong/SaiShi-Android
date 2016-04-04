package com.twtstudio.coder.saishi_android.ui.register.register;

/**
 * Created by clifton on 16-4-4.
 */
public interface RegisterPresenter {

    void validateRegister(String username, String sex, String age, String pwd,
                          String danwei, String zhiwu, String phone, String verify, String Cookie);
}
