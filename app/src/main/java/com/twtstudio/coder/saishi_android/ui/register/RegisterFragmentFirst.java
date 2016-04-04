package com.twtstudio.coder.saishi_android.ui.register;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.ui.register.register.RegisterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by clifton on 16-4-4.
 */
public class RegisterFragmentFirst extends Fragment implements View.OnClickListener{
    private final static String LOG_TAG = RegisterFragmentFirst.class.getSimpleName();
    private final static String MALE = "1";
    private final static String FEMALE = "0";

    @Bind(R.id.avatar_iv)
    ImageView iv_avatar;
    @Bind(R.id.register_et_username)
    EditText et_username;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.register_radioMale)
    AppCompatRadioButton male;
    @Bind(R.id.register_radioFemale)
    AppCompatRadioButton female;
    @Bind(R.id.register_et_danwei)
    EditText et_danwei;
    @Bind(R.id.register_et_zhiwu)
    EditText et_zhiwu;
    @Bind(R.id.register_btn_next)
    Button btn_next;

    private String username;
    private String sex = MALE;
    private String danwei;
    private String zhiwu;

    private RegisterActivityView registerActivityView;

    public RegisterFragmentFirst(){

    }


    public static RegisterFragmentFirst getInstance(){
        RegisterFragmentFirst registerFragmentFirst = new RegisterFragmentFirst();
        return registerFragmentFirst;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_first, container, false);
        ButterKnife.bind(this, rootView);
        registerActivityView = (RegisterActivityView)getActivity();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == male.getId()){
                    sex = MALE;
                    iv_avatar.setImageResource(R.mipmap.avatar_male);
                }else if(checkedId == female.getId()){
                    sex = FEMALE;
                    iv_avatar.setImageResource(R.mipmap.avatar_female);
                }
            }
        });
        btn_next.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v. getId()){
            case R.id.register_btn_next:
                if(!isAnyBlank()) {
                    postText();
                    replaceFragment(RegisterFragment.getInstance());
                }else {
                    toastMessage("请填写全部信息");
                }
                break;
        }
    }

    private boolean isAnyBlank(){
        if(et_username.getText().toString().equals("")
                || et_danwei.getText().toString().equals("")
                || et_zhiwu.getText().toString().equals("")){
            return true;
        }else {
            return false;
        }
    }

    private void postText(){
        username = et_username.getText().toString();
        danwei = et_danwei.getText().toString();
        zhiwu = et_zhiwu.getText().toString();
        registerActivityView.setText(username, sex, danwei, zhiwu);
    }

    private void replaceFragment(Fragment fragment){
        registerActivityView.showFragment(fragment);
    }

    private void toastMessage(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
