package com.twtstudio.coder.saishi_android.ui.content;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.support.JavascriptInterface;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.support.ExitApplication;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.support.StringUtils;
import com.twtstudio.coder.saishi_android.support.ImageHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;


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
    private void init() {
        dataItem = (DataItem) getIntent().getSerializableExtra("bean");
        mToolbarLayout.setTitle(dataItem.title);
        mToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        if (dataItem.url == null) {
            switch (PrefUtils.getPrefHeader()) {
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
        mWebView.addJavascriptInterface(new JavascriptInterface(this),
                "imagelistener");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        // 开启DOM storage API 功能
//        mWebView.getSettings().setDomStorageEnabled(false);
//        // 开启database storage API功能
//        mWebView.getSettings().setDatabaseEnabled(false);
//        // 开启Application Cache功能
//        mWebView.getSettings().setAppCacheEnabled(false);
        //设置单行显示
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //防止ScrollBar与WebView冲突，导致WebView出现大面积空白
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListener();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                // 监听器加载这是为了防止动态加载图片时新加载的图片无法预览
                addImageClickListener();
            }
        });
//        initWv();

        int a = 6;
        String html = "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "body {padding-left: " + a + "pt;padding-right: " + a + "pt;} " +
                "h2 {line-height: " + (a + 16) + "pt; text-align: center;}" +
                "</style> \n" +
                "</head> \n" +
                "<body><h2>" + dataItem.getTitle() + "</h2>" +
                "<p>" + dataItem.getContent() + "</p>" +
                "</body> \n </html>";

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        LogHelper.e(LOG_TAG, dataItem.content);

    }

//    public void initWv () {
//        if(Build.VERSION.SDK_INT >= 19) {
//            mWebView.getSettings().setLoadsImagesAutomatically(true);
//        } else {
//            mWebView.getSettings().setLoadsImagesAutomatically(false);
//        }
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                if(!mWebView.getSettings().getLoadsImagesAutomatically()) {
//                    mWebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//        });
//    }

    // 注入js函数监听
    private void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        String imageloadJS = getFromAssets("imageload.js");
        if (!TextUtils.isEmpty(imageloadJS)) {
            mWebView.loadUrl(imageloadJS);
        }
        LogHelper.e(LOG_TAG, "这里执行addImageClickListener");
    }

    // 读取assets中的文件
    private String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()
//                && event.getRepeatCount() == 0) {
//            /* 用户按返回键,如果可返回则回退 */
//            mWebView.goBack();
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            LogHelper.e(LOG_TAG, "webView is onDestroy! ");
        }
        super.onDestroy();
        ExitApplication.getInstance().removeActivity();
//        ContestApp.getRefWatcher().watch(this);
    }
}
