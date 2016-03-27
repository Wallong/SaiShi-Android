package com.twtstudio.coder.saishi_android.ui.login;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.UserInfo;
import com.twtstudio.coder.saishi_android.interactor.LoginInteractor;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;

/**
 * Created by clifton on 16-2-19.
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginCallback{

    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;
    private String password;

    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor){
        this.mLoginView = loginView;
        this.mLoginInteractor = loginInteractor;
    }

    @Override
    public void validateLogin(String username, String password) {
        mLoginView.hideKeyboard();
        if(!NetWorkHelper.isOnline()) {
            mLoginView.toastMessage("网络未连接");
            return;
        }
        mLoginView.showProgressBar();
        mLoginInteractor.login(username, password, this);
        this.password = password;
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        userInfo.setPassword(password);
        PrefUtils.setDefaultPrefUserInfo(userInfo);
        PrefUtils.setLogin(true);
        mLoginView.hideProgressBar();
        mLoginView.startMainActivity();
    }

    @Override
    public void onFailure(String errorCode) {
        mLoginView.hideProgressBar();
        switch (errorCode){
            case ApiClient.NO_USER_CODE:
                mLoginView.usernameError("用户名不存在");
                break;
            case ApiClient.PWD_ERROR_CODE:
                mLoginView.passwordError("密码错误");
                break;
            case ApiClient.LOG_IN_FAILURE_CODE:
                mLoginView.toastMessage("登录失败，请重试");
                break;
        }

    }
}
