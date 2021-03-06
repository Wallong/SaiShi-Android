package com.twtstudio.coder.saishi_android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.support.ExitApplication;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.StatusBarHelper;
import com.twtstudio.coder.saishi_android.ui.BaseActivity;
import com.twtstudio.coder.saishi_android.ui.main.MainActivity;
import com.twtstudio.coder.saishi_android.ui.register.RegisterActivity;

/**
 * Created by clifton on 16-2-19.
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginPresenter mLoginPresenter;
    @Bind(R.id.til_username)
    TextInputLayout mTilUsername;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.tv_login_username)
    AppCompatEditText mEtUsername;
    @Bind(R.id.tv_login_password)
    AppCompatEditText mEtPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.pb_login)
    ProgressBar mPbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mTilUsername.setHint("账户/手机");
        mTilPassword.setHint("密码");
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if(mEtUsername.getText().toString().equals("") || mEtPassword.getText().toString().equals("")){
                    toastMessage("请输入用户名和密码");
                }else {
                    mLoginPresenter.validateLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void usernameError(String errorString) {
        mEtUsername.setError(errorString);
    }

    @Override
    public void passwordError(String errorString) {
        mEtPassword.setError(errorString);
    }

    @Override
    public void showProgressBar() {
        mPbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new LoginModule(this));
    }

//    @Override
//    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
//        StatusBarHelper.setStatusBar(this);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.e(LOG_TAG,"leakLoginActivity");
//        ContestApp.getRefWatcher().watch(this);
    }


}
