package cn.edu.twt.saishi_android.ui.content;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.StringUtils;
import cz.msebera.android.httpclient.util.EncodingUtils;


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
        mToolbar.setTitle(dataItem.title);
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
        //设置缩放工具显示
        mWebView.getSettings().setBuiltInZoomControls(true);

        int a = 16;
        String html= "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "p {text-indent:2em; margin-top:"+(a+a)+"px}" +
                "body {text-align:justify; font-size: "+a+"px; line-height: "+(a+2)+"px}\n" +
                "body {padding-left: "+a+"px;padding-right: "+a+"px;} " +
                "</style> \n" +
                "</head> \n" +
                "<body><h3>" + dataItem.subtitle + "</h3>"+
                "<p>" + dataItem.content + "</p>" +
                "<p>"+StringUtils.cutString(dataItem.createtime, 3) +"</p>" +"</body> \n </html>";

        mWebView.loadDataWithBaseURL(ApiClient.getBaseUrl(), html, "text/html", "UTF-8", null);

    }
}
