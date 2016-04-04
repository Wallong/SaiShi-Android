package com.twtstudio.coder.saishi_android.ui.register.register;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.NormalInfo;
import com.twtstudio.coder.saishi_android.interactor.RegisterInteractor;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;

/**
 * Created by clifton on 16-4-4.
 */
public class RegisterPresenterImpl implements RegisterPresenter, OnRegisterCallback {
    private final static String LOG_TAG = RegisterPresenterImpl.class.getSimpleName();

    private RegisterView _registerView;
    private RegisterInteractor _registerInteractor;

    public RegisterPresenterImpl(RegisterView registerView, RegisterInteractor registerInteractor) {
        this._registerView = registerView;
        this._registerInteractor = registerInteractor;
    }

    @Override
    public void validateRegister(String username, String sex, String age, String pwd, String danwei, String zhiwu, String phone, String verify, String cookie) {
        if(!NetWorkHelper.isOnline()) {
            _registerView.toastMessage("网络未连接");
            return;
        }
        _registerInteractor.register(username, sex, age, pwd, danwei, zhiwu, phone, verify, cookie, this);
        _registerView.showProgressBar();
    }

    @Override
    public void onSuccess(NormalInfo normalInfo) {
        LogHelper.e(LOG_TAG, normalInfo.toString());
        _registerView.hideProgressBar();
        if(normalInfo.getResult_code().equals(ApiClient.REGISTER_SUCCESS_CODE)) {
            _registerView.finishActivity();
        }else {
            _registerView.toastMessage(normalInfo.getMsg());
        }
    }

    @Override
    public void onFailure(String errorString) {
        LogHelper.e(LOG_TAG, errorString);
        _registerView.toastMessage("注册失败，请重试～");
    }
}
