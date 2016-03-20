package cn.edu.twt.saishi_android.ui.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.UpdateInfo;
import cn.edu.twt.saishi_android.interactor.SettingsInteractorImpl;
import cn.edu.twt.saishi_android.support.CacheDbHelper;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.NetWorkHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.schedule.ScheduleFragment;
import cn.edu.twt.saishi_android.ui.settings.OnUpdateCallback;

public class MainActivity extends AppCompatActivity
        implements MainView{

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fl_content)
    FrameLayout mFrameLayout;

    private final static String TYPE = "0";

    private long firstTime;
    private Toolbar toolbar;
    private CacheDbHelper dbHelper;
    private SettingsInteractorImpl settingsInteractorImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("日程");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        dbHelper = new CacheDbHelper(this, 1);

        getFragmentManager().beginTransaction().commit();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content,
                        new ScheduleFragment()).commit();

        if(!NetWorkHelper.isOnline()) {
            toastMessage("网络未连接");
        }else {
            checkForUpdate(TYPE);
        }
    }

    @Override
    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    public CacheDbHelper getDbHelper(){
        return dbHelper;
    }


    @Override
    public void setToolbar(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeMenu();
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(mFrameLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                sb.show();
                firstTime = secondTime;
            } else {
                ExitApplication.getInstance().exit();
            }
        }

    }
    @Override
    public void checkForUpdate(String type) {
        settingsInteractorImpl = new SettingsInteractorImpl();
        settingsInteractorImpl.getUpdateInfo(type, new OnUpdateCallback() {
            @Override
            public void onSuccess(UpdateInfo updateInfo) {
                if(updateInfo.getResult_code().equals(ApiClient.UPDATE_NEW_CODE)){
                    PrefUtils.setDefaultPrefUpdate(ApiClient.UPDATE_NEW_CODE);
                    toastMessage(updateInfo.getMsg());
                }
            }

            @Override
            public void onFailure(String errorString) {

            }
        });
    }

}