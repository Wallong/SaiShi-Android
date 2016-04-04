package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.NormalInfo;
import com.twtstudio.coder.saishi_android.ui.register.register.OnRegisterCallback;

import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-4-4.
 */
public class RegisterInteractorImpl implements RegisterInteractor {
    @Override
    public void register(String username, String sex, String age, String pwd, String danwei, String zhiwu, String phone, String verify, String cookie, final OnRegisterCallback onRegisterCallback) {
        ApiClient.register(username, sex, age, pwd, danwei, zhiwu, phone, verify, cookie, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onRegisterCallback.onFailure("注册失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                NormalInfo normalInfo = gson.fromJson(responseString, NormalInfo.class);
                onRegisterCallback.onSuccess(normalInfo);
            }
        });
    }
}
