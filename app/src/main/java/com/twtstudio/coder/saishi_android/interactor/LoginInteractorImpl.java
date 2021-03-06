package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.UserInfo;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.login.OnLoginCallback;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-2-21.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private static final String LOG_TAG = LoginInteractorImpl.class.getSimpleName();

    @Override
    public void login(String username, String password, final OnLoginCallback onLoginCallback) {

        ApiClient.userLogin(username, password, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());
                try {
                    String resultCode = response.getString(ApiClient.RESP_RESULT_CODE_KEY);
                    if(resultCode.equals(ApiClient.LOG_IN_SUCCESS_CODE)) {
                            Gson gson = new Gson();
                            UserInfo userInfo = gson.fromJson(response.toString(), UserInfo.class);
                            ApiClient.getUserInfo(new TextHttpResponseHandler() {
                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    LogHelper.v(LOG_TAG, throwable.toString());
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    Gson gson = new Gson();
                                    UserInfo userInfo = gson.fromJson(responseString, UserInfo.class);
                                    LogHelper.e(LOG_TAG, responseString);
                                    onLoginCallback.onSuccess(userInfo);
                                }
                            });
//                            onLoginCallback.onSuccess(userInfo);
                    }else {
                        onLoginCallback.onFailure(resultCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                LogHelper.v(LOG_TAG, throwable.toString());
            }
        });
    }
}
