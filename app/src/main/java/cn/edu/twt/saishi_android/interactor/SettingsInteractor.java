package cn.edu.twt.saishi_android.interactor;

import cn.edu.twt.saishi_android.ui.settings.OnUpdateCallback;

/**
 * Created by clifton on 16-3-17.
 */
public interface SettingsInteractor {

    void getUpdateInfo(String type, OnUpdateCallback onUpdateCallback);
}
