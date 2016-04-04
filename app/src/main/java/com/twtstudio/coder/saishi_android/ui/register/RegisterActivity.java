package com.twtstudio.coder.saishi_android.ui.register;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.support.LogHelper;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by clifton on 16-3-30.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterActivityView{
    private final static String LOG_TAG = RegisterActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private Fragment mFragment;
    private String username;
    private String sex;
    private String danwei;
    private String zhiwu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mToolbar.setTitle("注册");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mFragment = RegisterFragmentFirst.getInstance();
        showFragment(mFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.register_fragment, fragment).commit();
        LogHelper.e(LOG_TAG, "transaction");
    }

    @Override
    public void setText(String username, String sex, String danwei, String zhiwu) {
        this.username = username;
        this.sex = sex;
        this.danwei = danwei;
        this.zhiwu = zhiwu;
        LogHelper.e(LOG_TAG, username + sex + danwei + zhiwu);
    }

    @Override
    public HashMap getText() {
        HashMap firstPage = new HashMap();
        firstPage.put("username", username);
        firstPage.put("sex", sex);
        firstPage.put("danwei", danwei);
        firstPage.put("zhiwu", zhiwu);
        return firstPage;
    }

}
