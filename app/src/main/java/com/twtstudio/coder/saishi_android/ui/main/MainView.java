package com.twtstudio.coder.saishi_android.ui.main;

/**
 * Created by clifton on 16-2-27.
 */
public interface MainView {

    void checkForUpdate(String type);

    void closeMenu();

    void setToolbar(String title);

    void toastMessage(String msg);

}
