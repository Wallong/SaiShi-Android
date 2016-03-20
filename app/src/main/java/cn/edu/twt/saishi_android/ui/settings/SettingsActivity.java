package cn.edu.twt.saishi_android.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.UpdateInfo;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.BaseActivity;
import cn.edu.twt.saishi_android.ui.common.ImageHelper;
import cn.edu.twt.saishi_android.ui.login.LoginActivity;
import cn.edu.twt.saishi_android.ui.settings.modify.ModifyActivity;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by clifton on 16-2-28.
 */
public class SettingsActivity extends BaseActivity implements SettingsView, View.OnClickListener {
    private final static String LOG_TAG = SettingsActivity.class.getSimpleName();
    private final static String TYPE = "0";

    @Inject
    SettingsPresenter mSettingsPresenter;
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
        tv_phone.setText("电话:" + PrefUtils.getPrefPhone());
        tv_position.setText("单位:" + PrefUtils.getPrefDanwei());

        if (PrefUtils.getPrefUpdate() != null && PrefUtils.getPrefUpdate().equals(ApiClient.UPDATE_NEW_CODE)){
            iv_version.setImageResource(R.drawable.ic_version);
        }

        ll_version.setOnClickListener(this);
        ll_modify.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new SettingsModule(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_version:
                mSettingsPresenter.getUpdateInfo(TYPE);
                break;
            case R.id.ll_modify:
                Intent intent = new Intent(SettingsActivity.this, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_logout:
                showDialog("0", null);
                break;
        }

    }


    @Override
    public void showDialog(final String tag, UpdateInfo updateInfo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (tag) {
            case "0":
                builder.setMessage("是否注销该账户？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApiClient.userLogout(new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                                LogHelper.v("zhuxiao-------------＞", "注销成功" + responseString);
                                startActivity(intent);
                                ExitApplication.getInstance().logout();
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case "1":
                builder.setTitle("新版本更新");
                String msg = "检测到新版本: " + updateInfo.getVersion() + "\n" + updateInfo.getDetail();
                final String url = updateInfo.getUrl();
                builder.setMessage(msg);
                builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
        }
        builder.show();
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    public static String getVersionName(Context context) {
//        return getPackageInfo(context).versionName;
//    }
//
//    //版本号
//    public static int getVersionCode(Context context) {
//        return getPackageInfo(context).versionCode;
//    }
//
//    private static PackageInfo getPackageInfo(Context context) {
//        PackageInfo pi = null;
//
//        try {
//            PackageManager pm = context.getPackageManager();
//            pi = pm.getPackageInfo(context.getPackageName(),
//                    PackageManager.GET_CONFIGURATIONS);
//
//            return pi;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return pi;
//    }
}
