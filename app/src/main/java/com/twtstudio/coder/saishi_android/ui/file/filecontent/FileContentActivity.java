package com.twtstudio.coder.saishi_android.ui.file.filecontent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.bean.FileInfo;
import com.twtstudio.coder.saishi_android.support.ExitApplication;

/**
 * Created by clifton on 16-3-11.
 */
public class FileContentActivity extends AppCompatActivity {
    @Bind(R.id.wv_file_content)
    WebView mWebView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private FileInfo fileInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_content);
        ButterKnife.bind(this);
        ExitApplication.getInstance().addActivity(this);
        init();
    }

    private void init(){
        fileInfo = (FileInfo)getIntent().getSerializableExtra("bean");
        mToolbar.setTitle(fileInfo.title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
//                "h2 {text-align:center; line-height: "+(a+10)+"pt}" +
                "p {line-height: "+(a+6)+"pt}" +
                "</style> \n" +
                "</head> \n" +
                "<body>" +
                "<p>" + fileInfo.content + "</p>" +
                "</body> \n </html>";

        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitApplication.getInstance().removeActivity();
//        ContestApp.getRefWatcher().watch(this);
    }
}
