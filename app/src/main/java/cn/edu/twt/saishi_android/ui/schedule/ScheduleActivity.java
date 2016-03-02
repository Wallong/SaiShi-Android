package cn.edu.twt.saishi_android.ui.schedule;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;


import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.ContestApp;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.support.ExitApplication;

/**
 * Created by clifton on 16-2-27.
 */
public class ScheduleActivity extends AppCompatActivity{

    private static final String LOG_TAG = ScheduleActivity.class.getSimpleName();

    @Bind(R.id.wv_schedule)
    WebView mWebView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        ContestApp.setAppLunchState(true);
        ExitApplication.getInstance().addActivity(this);

        mToolbar.setTitle("日程");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mWebView = (WebView) findViewById(R.id.wv_schedule);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //设置缩放工具显示
        mWebView.getSettings().setBuiltInZoomControls(true);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(ApiClient.SCHEDULE_URL);
            }
        });
    }

}
