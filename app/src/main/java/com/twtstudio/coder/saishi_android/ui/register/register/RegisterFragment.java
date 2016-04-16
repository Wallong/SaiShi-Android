package com.twtstudio.coder.saishi_android.ui.register.register;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.BaseFragment;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.ui.register.RegisterActivityView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by clifton on 16-4-4.
 */
public class RegisterFragment extends BaseFragment implements RegisterView, View.OnClickListener {
    private final static String LOG_TAG = RegisterFragment.class.getSimpleName();
    private final static String MALE = "1";
    private final static String FEMALE = "0";

    @Inject
    RegisterPresenter registerPresenter;
    @Bind(R.id.avatar_iv)
    ImageView iv_avatar;
    @Bind(R.id.register_et_phone)
    EditText et_phone;
    @Bind(R.id.register_et_pwd)
    EditText et_pwd;
    @Bind(R.id.register_et_pwd_repeat)
    EditText et_pwd_repeat;
    @Bind(R.id.register_iv_verify)
    ImageView iv_verify;
    @Bind(R.id.register_et_verify)
    EditText et_verify;
    @Bind(R.id.register_btn)
    Button btn_register;
    @Bind(R.id.register_progressbar)
    ProgressBar progressBar;

    private String username;
    private String sex;
    private String danwei;
    private String zhiwu;
    private String cookie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_second, container, false);
        ButterKnife.bind(this, rootView);

        getTextFromFirstPage();
        setImage();
        et_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!et_pwd.getText().toString().equals("") && !et_pwd_repeat.getText().toString().equals("")
                        && !s.toString().equals("")){
                    btn_register.setBackgroundResource(R.drawable.btn_selector);
                    btn_register.setOnClickListener(RegisterFragment.this);
                }else {
                    btn_register.setBackgroundResource(R.drawable.btn_selector_modify);
                    btn_register.setOnClickListener(null);
                }
            }
        });
        iv_verify.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public RegisterFragment(){

    }


    public static RegisterFragment getInstance(){
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }


    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new RegisterModule(this));
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                String phone = et_phone.getText().toString();
                String pwd = et_pwd.getText().toString();
                String pwd_repeat = et_pwd_repeat.getText().toString();
                String verify = et_verify.getText().toString();
                if(pwd.equals("")){
                    toastMessage("密码为空");
                }else if(!pwd.equals(pwd_repeat)) {
                    toastMessage("两次输入密码不一致");
                }else if(phone.length()!=11){
                    toastMessage("手机号码格式不正确");
                }else {
                    registerPresenter.validateRegister(username, sex, "", pwd, danwei, zhiwu, phone, verify, cookie);
                }
                break;
            case R.id.register_iv_verify:
                setImage();
                break;
        }
    }

    private void getTextFromFirstPage(){
        RegisterActivityView registerActivityView = (RegisterActivityView) getActivity();
        HashMap<String, String> firstPage = registerActivityView.getText();
        this.username = firstPage.get("username");
        this.sex = firstPage.get("sex");
        this.danwei = firstPage.get("danwei");
        this.zhiwu = firstPage.get("zhiwu");
        if(sex.equals(MALE)){
            iv_avatar.setImageResource(R.mipmap.avatar_male);
        }else {
            iv_avatar.setImageResource(R.mipmap.avatar_female);
        }
    }

    private void setImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap downloadBitmap = getBitmap();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_verify.setImageBitmap(downloadBitmap);
                            LogHelper.e(LOG_TAG, "uiThread------->" + Thread.currentThread().getId());
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                LogHelper.e(LOG_TAG, "new Thread------>" + Thread.currentThread().getId());
            }
        }).start();
    }

    public Bitmap getBitmap() throws IOException {
        String COOKIE;
        HttpUriRequest request = new HttpPost(ApiClient.getBaseUrl() + ApiClient.getVerifyUrl());
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);
        COOKIE = ((AbstractHttpClient) client).getCookieStore().getCookies().get(0).getValue();
        cookie = "PHPSESSID=" + COOKIE;
        LogHelper.e(LOG_TAG, cookie);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
            return bitmap;
        } else {
            throw new IOException("Download failed, HTTP response code "
                    + statusCode + " - " + statusLine.getReasonPhrase());
        }
    }
}
