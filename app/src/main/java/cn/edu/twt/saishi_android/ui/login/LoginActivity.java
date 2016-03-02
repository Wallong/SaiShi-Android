package cn.edu.twt.saishi_android.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.StatusBarHelper;
import cn.edu.twt.saishi_android.ui.BaseActivity;
import cn.edu.twt.saishi_android.ui.main.MainActivity;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-2-19.
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener{

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginPresenter mLoginPresenter;
    @Bind(R.id.tv_login_username)
    EditText mEtUsername;
    @Bind(R.id.tv_login_password)
    EditText mEtPassword;
    @Bind(R.id.btn_login)
//    Button mBtnLogin;
//    @Bind(R.id.btn_test)
    Button mBtnTest;
    @Bind(R.id.pb_login)
    ProgressBar mPbLogin;
    @Bind(R.id.tv_question)
    TextView mTvQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(this);

//        mBtnLogin.setOnClickListener(this);
        mBtnTest.setOnClickListener(this);
        mTvQuestion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mLoginPresenter.validateLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
                break;
            case R.id.tv_question:
                toastMessage("初始账户和密码均为您的手机号");
                break;
//            case R.id.btn_test:
//                ApiClient.getUserInfo(new TextHttpResponseHandler() {
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        LogHelper.v(LOG_TAG, responseString);
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                        LogHelper.v(LOG_TAG, responseString);
//                    }
//                });
//                break;
        }

    }

    @Override
    public void usernameError(String errorString) {

    }

    @Override
    public void passwordError(String errorString) {

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

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarHelper.setStatusBar(this);
    }


}
