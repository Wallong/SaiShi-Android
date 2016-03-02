package cn.edu.twt.saishi_android.ui.settings.modify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.BaseActivity;

/**
 * Created by clifton on 16-2-29.
 */
public class ModifyActivity extends BaseActivity implements View.OnClickListener, ModifyView {


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
        modify_btn.setOnClickListener(this);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
