package com.twtstudio.coder.saishi_android.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.UpdateInfo;
import com.twtstudio.coder.saishi_android.interactor.SettingsInteractorImpl;
import com.twtstudio.coder.saishi_android.support.CacheDbHelper;
import com.twtstudio.coder.saishi_android.support.DeviceUtils;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.ui.settings.OnUpdateCallback;

public class MainActivity extends AppCompatActivity implements MainView{
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fl_content)
    FrameLayout mFrameLayout;

    private final static String TYPE = "0";

    private long firstTime;
    private Toolbar toolbar;
    private CacheDbHelper dbHelper;
    private SettingsInteractorImpl settingsInteractorImpl;
    private WebView webview;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        dbHelper = new CacheDbHelper(this, 1);
        toolbar.setTitle("日程");

//        getFragmentManager().beginTransaction().commit();
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fl_content,
//                        new ScheduleFragment()).commit();

        if(!NetWorkHelper.isOnline()) {
            toastMessage("网络未连接");
        }else {
            checkForUpdate(TYPE);
        }
    }

    @Override
    public void closeMenu() {
        //首次运行，MenuFragment比MainActivity先创建
        if(mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }

    public CacheDbHelper getDbHelper(){
        return dbHelper;
    }


    @Override
    public void setToolbar(String title) {
        //首次运行，MenuFragment比MainActivity先创建
        if(toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeMenu();
        } else if(webview.canGoBack() && position == 2){
            webview.goBack();
        } else {
            long secondTime = System.currentTimeMillis();
            LogHelper.e(LOG_TAG, "This id the ...currentTimeMillis" + secondTime);
            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(mFrameLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                sb.show();
                firstTime = secondTime;
            } else {
                this.finish();
            }
        }

    }


    @Override
    public void checkForUpdate(String type) {
        settingsInteractorImpl = new SettingsInteractorImpl();
        settingsInteractorImpl.getUpdateInfo(type, new OnUpdateCallback() {
            @Override
            public void onSuccess(UpdateInfo updateInfo) {
                //测试一下更新系统
                updateInfo.setResult_code(ApiClient.UPDATE_NEW_CODE);
                updateInfo.setDetail("1.本次更新加入了夜间模式  \n 2.好吧，我也不知道我想说啥子");
                updateInfo.setUrl("http://fir.im/1a8p");
                updateInfo.setVersion("1.5.2");
                if(updateInfo.getResult_code().equals(ApiClient.UPDATE_NEW_CODE ) && updateInfo.getVersion().equals(DeviceUtils.getVersionName())){
                    PrefUtils.setDefaultPrefUpdate(ApiClient.UPDATE_NEW_CODE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("新版本更新");
                    String msg = "检测到新版本: " + updateInfo.getVersion() + "\n\n" + updateInfo.getDetail();
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
                    builder.show();
                } else {
                    PrefUtils.setDefaultPrefUpdate(ApiClient.UPDATE_NO_CODE);
                }
            }

            @Override
            public void onFailure(String errorString) {
                PrefUtils.setDefaultPrefUpdate(ApiClient.UPDATE_NO_CODE);
            }
        });
    }
    @Override
    public void initWebView(WebView webview){
        this.webview = webview;
    }
    @Override
    public void setFragment(int position){
        this.position = position;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ContestApp.getRefWatcher().watch(this);
    }

}