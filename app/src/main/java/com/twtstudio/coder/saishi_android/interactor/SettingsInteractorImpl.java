package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.UpdateInfo;
import com.twtstudio.coder.saishi_android.ui.settings.OnUpdateCallback;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-3-17.
 */
public class SettingsInteractorImpl implements SettingsInteractor {
    @Override
    public void getUpdateInfo(String type, final OnUpdateCallback onUpdateCallback) {
        ApiClient.update(type, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onUpdateCallback.onFailure("请求错误");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if(responseString != null) {
                    Gson gson = new Gson();
                    UpdateInfo updateInfo = gson.fromJson(responseString, UpdateInfo.class);
                    onUpdateCallback.onSuccess(updateInfo);
                }else {
                    onUpdateCallback.onFailure("请求失败");
                }
            }
        });
    }
}
