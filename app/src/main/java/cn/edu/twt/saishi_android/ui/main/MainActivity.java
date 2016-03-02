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
import cn.edu.twt.saishi_android.ContestApp;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.ImageInfo;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.common.ImageHelper;
import cn.edu.twt.saishi_android.ui.main.list.DataFragment;

public class MainActivity extends AppCompatActivity
        implements MainView{

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fl_content)
    FrameLayout mFrameLayout;

    private long firstTime;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ContestApp.setAppLunchState(true);
        ExitApplication.getInstance().addActivity(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, DataFragment.getInstance("Tongzhi")).commit();

    }


    @Override
    public void replaceFragment(String type) {

    }

    @Override
    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void startScheduleActivity() {

    }

    @Override
    public void startSettingsActivity() {

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
                finish();
            }
        }

    }

}