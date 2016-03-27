package com.twtstudio.coder.saishi_android.ui.settings;

import com.twtstudio.coder.saishi_android.bean.UpdateInfo;

/**
 * Created by clifton on 16-2-28.
 */
public interface SettingsView {

    void showDialog(String tag, UpdateInfo updateInfo);

    void toastMessage(String msg);

}
