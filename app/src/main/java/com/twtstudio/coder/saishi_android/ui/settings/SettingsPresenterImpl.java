package com.twtstudio.coder.saishi_android.ui.settings;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.UpdateInfo;
import com.twtstudio.coder.saishi_android.interactor.SettingsInteractor;
import com.twtstudio.coder.saishi_android.support.DeviceUtils;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;

/**
 * Created by clifton on 16-2-28.
 */
public class SettingsPresenterImpl implements SettingsPresenter, OnUpdateCallback{
    private final static String LOG_TAG = SettingsPresenterImpl.class.getSimpleName();

    private SettingsView mSettingsView;
    private SettingsInteractor mSettingsInteractor;

    public SettingsPresenterImpl(SettingsView settingsView, SettingsInteractor settingsInteractor) {
        this.mSettingsView = settingsView;
        this.mSettingsInteractor = settingsInteractor;
    }

    @Override
    public void getUpdateInfo(String type) {
        if(!NetWorkHelper.isOnline()) {
            mSettingsView.toastMessage("网络未连接");
            return;
        }
        mSettingsInteractor.getUpdateInfo(type, this);
    }

    @Override
    public void onSuccess(UpdateInfo updateInfo) {
//        {result_code='10000', msg='已经是最新版', version='null', detail='null', url='null'}
        LogHelper.v(LOG_TAG, updateInfo);
        if(updateInfo.getResult_code().equals(ApiClient.UPDATE_NO_CODE)) {
            mSettingsView.toastMessage(updateInfo.getMsg());
        } else if (updateInfo.getUrl() != null && updateInfo.getResult_code().equals(ApiClient.UPDATE_NEW_CODE) && updateInfo.getVersion().equals(DeviceUtils.getVersionName())) {
            //跳转浏览器
            mSettingsView.showDialog("1", updateInfo);
        }
    }

    @Override
    public void onFailure(String errorString) {
        LogHelper.v(LOG_TAG, errorString);
    }

}
