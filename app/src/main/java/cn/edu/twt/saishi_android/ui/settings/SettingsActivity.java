package cn.edu.twt.saishi_android.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.common.ImageHelper;
import cn.edu.twt.saishi_android.ui.login.LoginActivity;
import cn.edu.twt.saishi_android.ui.settings.modify.ModifyActivity;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by clifton on 16-2-28.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.ll_version)
    LinearLayout ll_version;
    @Bind(R.id.setting_version)
    ImageView iv_version;
    @Bind(R.id.ll_modify)
    LinearLayout ll_modify;
    @Bind(R.id.ll_logout)
    LinearLayout ll_logout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.user_profile_setting_image)
    CircleImageView iv_icon;
    @Bind(R.id.tv_setting_username)
    TextView tv_phone;
    @Bind(R.id.tv_user_setting_name)
    TextView tv_name;
    @Bind(R.id.tv_user_setting_position)
    TextView tv_position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(this);

        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageHelper.getImageLoder().displayImage(ApiClient.getBaseUrl() + PrefUtils.getPrefIconUrl(),
                iv_icon, ImageHelper.getDisplayImageOptions());

        tv_name.setText(PrefUtils.getPrefUsername());
        tv_phone.setText(PrefUtils.getPrefPhone());
        tv_position.setText("单位:" + PrefUtils.getPrefDanwei());

        iv_version.setImageResource(R.drawable.ic_version);

        ll_version.setOnClickListener(this);
        ll_modify.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_version:
                break;
            case R.id.ll_modify:
                Intent intent = new Intent(SettingsActivity.this, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_logout:
                showDialog("0");
                break;
        }

    }

    private void showDialog(final String tag){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否注销该账户？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(tag.equals("0")){
                    ApiClient.userLogout(new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                            LogHelper.v("zhuxiao-------------＞", "注销成功"+responseString);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

}
