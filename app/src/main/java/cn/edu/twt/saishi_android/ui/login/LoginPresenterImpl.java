package cn.edu.twt.saishi_android.ui.login;

import cn.edu.twt.saishi_android.bean.UserInfo;
import cn.edu.twt.saishi_android.interactor.LoginInteractor;
import cn.edu.twt.saishi_android.support.NetWorkHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;

/**
 * Created by clifton on 16-2-19.
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginCallback{

    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

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
    }

    @Override
    public void onSuccess(UserInfo userInfo) {
        PrefUtils.setDefaultPrefUserInfo(userInfo);
        PrefUtils.setLogin(true);
        mLoginView.hideProgressBar();
        mLoginView.startMainActivity();
    }

    @Override
    public void onFailure(String errorString) {
        mLoginView.hideProgressBar();
        mLoginView.toastMessage(errorString);

    }
}
