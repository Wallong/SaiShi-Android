package com.twtstudio.coder.saishi_android.ui.content;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.support.ExitApplication;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.support.StringUtils;
import com.twtstudio.coder.saishi_android.ui.common.ImageHelper;


/**
 * Created by clifton on 16-2-28.
 */
public class ContentActivity extends AppCompatActivity {
    private final static String LOG_TAG = ContentActivity.class.getSimpleName();

    @Bind(R.id.wv_content)
    WebView mWebView;
    @Bind(R.id.tv_content_time)
    TextView mTvContentTime;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.toolbar_back)
    ImageView mIvToolbar;

    private DataItem dataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(this);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        dataItem = (DataItem)getIntent().getSerializableExtra("bean");
        mToolbarLayout.setTitle(dataItem.title);
        mToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        if(dataItem.url == null){
            switch (PrefUtils.getPrefHeader()){
                case "0":
                    mIvToolbar.setImageResource(R.mipmap.ic_drawer_header_old);
                    break;
                case "1":
                    mIvToolbar.setImageResource(R.mipmap.ic_drawer_header_new);
                    break;
            }
        }

        ImageHelper.getImageLoder().displayImage(
                ApiClient.getBaseUrl() + dataItem.url,
                mIvToolbar, ImageHelper.getDisplayImageOptions());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvContentTime.setText(StringUtils.cutString(dataItem.createtime, 0));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        //设置单行显示
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //防止ScrollBar与WebView冲突，导致WebView出现大面积空白
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);

        int a = 6;
        String html= "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "body {padding-left: "+a+"pt;padding-right: "+a+"pt;} " +
                "h2 {line-height: "+(a+16)+"pt; text-align: center;}" +
                "</style> \n" +
                "</head> \n" +
                "<body><h2>" + dataItem.title + "</h2>"+
                "<p>" + dataItem.content + "</p>" +
                "</body> \n </html>";

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        LogHelper.e(LOG_TAG, dataItem.content);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity();
//        ContestApp.getRefWatcher().watch(this);
    }
}
