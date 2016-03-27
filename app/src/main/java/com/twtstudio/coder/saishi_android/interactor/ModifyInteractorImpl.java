package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.NormalInfo;
import com.twtstudio.coder.saishi_android.ui.settings.modify.OnGetPwdChangedCallback;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-2-29.
 */
public class ModifyInteractorImpl implements ModifyInteractor{

    @Override
    public void changePwd(String oldPwd, String newPwd, final OnGetPwdChangedCallback onGetPwdChangedCallback) {
        ApiClient.modifyPwd(oldPwd, newPwd, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onGetPwdChangedCallback.onFailure(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                NormalInfo normalInfo = gson.fromJson(responseString, NormalInfo.class);
                onGetPwdChangedCallback.onSuccess(normalInfo);
            }
        });
    }
}
