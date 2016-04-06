package com.twtstudio.coder.saishi_android.ui.settings.modify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.support.ExitApplication;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.ui.BaseActivity;

/**
 * Created by clifton on 16-2-29.
 */
public class ModifyActivity extends BaseActivity implements View.OnClickListener, ModifyView {
    private static final String LOG_TAG = ModifyActivity.class.getSimpleName();

    @Inject
    ModifyPresenter mModifyPresenter;
    @Bind(R.id.modify_phone)
    TextView tv_modify_phone;
    @Bind(R.id.modify_old_pwd)
    EditText ev_old_pwd;
    @Bind(R.id.modify_new_pwd1)
    EditText ev_new_pwd1;
    @Bind(R.id.modify_new_pwd2)
    EditText ev_new_pwd2;
    @Bind(R.id.modify_btn)
    Button modify_btn;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_modify);
        ButterKnife.bind(this);

        mToolbar.setTitle("修改密码");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_modify_phone.setText(PrefUtils.getPrefPhone());


        ev_old_pwd.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (ev_old_pwd.getText().toString().equals(PrefUtils.getPrefPassword())) {
                        toastMessage("请输入新密码");
                    } else {
                        toastMessage("当前密码错误请重新填写");
                    }
                }
            }
        });

        ev_new_pwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().equals(ev_new_pwd1.getText().toString())) {
                    modify_btn.setBackgroundResource(R.drawable.btn_selector);
                    modify_btn.setOnClickListener(ModifyActivity.this);
                }else {
                    modify_btn.setBackgroundResource(R.drawable.btn_selector_modify);
                    modify_btn.setOnClickListener(null);
                }
            }
        });
        ev_new_pwd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().equals(ev_new_pwd2.getText().toString())){
                    modify_btn.setBackgroundResource(R.drawable.btn_selector);
                    modify_btn.setOnClickListener(ModifyActivity.this);
                }else {
                    modify_btn.setBackgroundResource(R.drawable.btn_selector_modify);
                    modify_btn.setOnClickListener(null);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        String oldPwd = ev_old_pwd.getText().toString();
        String newPwd1 = ev_new_pwd1.getText().toString();
        String newPwd2 = ev_new_pwd2.getText().toString();

        if(newPwd1.equals(newPwd2) && !newPwd1.equals("")){
            mModifyPresenter.validateChangePwd(oldPwd, newPwd1);
        } else if(newPwd1.equals("") || newPwd1.equals("")){
            toastMessage("请输入密码");
        } else {
            toastMessage("两次密码输入不一致,请重新输入");
        }
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ModifyModule(this));
    }

    @Override
    public void showProgessBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void toastMessage(String msg) {
        if(msg.equals("密码修改成功")){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            PrefUtils.setPrePassword(ev_new_pwd2.getText().toString());
            this.finish();
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
