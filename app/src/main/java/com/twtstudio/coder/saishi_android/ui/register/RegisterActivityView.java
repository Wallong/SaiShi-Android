package com.twtstudio.coder.saishi_android.ui.register;

import android.app.Fragment;

import java.util.HashMap;

/**
 * Created by clifton on 16-4-4.
 */
public interface RegisterActivityView {

    void showFragment(Fragment fragment);

    void setText(String username, String sex, String danwei, String zhiwu);

    HashMap getText();
}
