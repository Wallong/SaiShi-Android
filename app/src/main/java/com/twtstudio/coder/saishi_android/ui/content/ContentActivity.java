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

import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.DataItem;
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
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        dataItem = (DataItem)getIntent().getSerializableExtra("bean");
        mToolbarLayout.setTitle(dataItem.title);
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
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);

        int a = 12;
        String html= "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "p {text-indent:2em; margin-top:"+(a+a)+"pt}" +
                "body {text-align:justify; font-size: "+a+"pt;}\n" +
                "body {padding-left: "+a+"pt;padding-right: "+a+"pt;} " +
                "h3 {line-height: "+(a+10)+"pt}" +
                "p {line-height: "+(a+6)+"pt}" +
                "</style> \n" +
                "</head> \n" +
                "<body><h3>" + dataItem.subtitle + "</h3>"+
                "<p>" + dataItem.content + "</p>" +
                "</body> \n </html>";

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ContestApp.getRefWatcher().watch(this);
    }
}
