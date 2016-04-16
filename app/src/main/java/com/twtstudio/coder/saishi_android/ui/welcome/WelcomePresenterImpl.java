package com.twtstudio.coder.saishi_android.ui.welcome;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.support.PrefUtils;

/**
 * Created by clifton on 16-4-16.
 */
public class WelcomePresenterImpl implements WelcomePresenter {
    @Override
    public void reLogin() {
        if(isReLogin()){
            ApiClient.userLogin(PrefUtils.getPrefPhone(), PrefUtils.getPrefPassword(), new JsonHttpResponseHandler());
        }
    }

    //判断上次是否正常注销以及用户名和密码是否为空
    private boolean isReLogin(){
        return PrefUtils.isLogin() && PrefUtils.getPrefPhone() != null && PrefUtils.getPrefPassword() != null;
    }
}
