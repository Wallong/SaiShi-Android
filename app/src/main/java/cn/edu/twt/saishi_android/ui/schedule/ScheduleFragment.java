package cn.edu.twt.saishi_android.ui.schedule;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.api.ApiClient;

/**
 * Created by clifton on 16-2-27.
 */
public class ScheduleFragment extends Fragment {

    private static final String LOG_TAG = ScheduleFragment.class.getSimpleName();

    @Bind(R.id.wv_schedule)
    WebView mWebView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    WebChromeClient wcc = new WebChromeClient(){

        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
            view.requestFocus();

        }


    };

    WebViewClient wvc = new WebViewClient(){

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);
        loadSchedule();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        return view;
    }



    private void loadSchedule(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
//        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //设置缩放工具显示
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.setWebViewClient(wvc);
        mWebView.setWebChromeClient(wcc);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (v.getId()) {
                    case R.id.wv_schedule:
                        mWebView.requestFocus();
                        break;
                }
                return false;
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(ApiClient.SCHEDULE_URL);
            }
        });
    }

    private void goBack(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }
    }

}
