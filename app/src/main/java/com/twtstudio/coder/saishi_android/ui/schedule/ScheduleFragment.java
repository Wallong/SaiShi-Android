package com.twtstudio.coder.saishi_android.ui.schedule;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.LocationBaseFragment;
import com.twtstudio.coder.saishi_android.ui.main.MainView;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by clifton on 16-4-6.
 */
public class ScheduleFragment extends LocationBaseFragment {
    private static final String LOG_TAG = ScheduleFragment.class.getSimpleName();

    @Bind(R.id.wv_schedule)
    WebView mWebView;

    private MainView mainView;

    private ProgressDialog progressDialog;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, rootView);
        mainView = (MainView)getActivity();
        initWebView();
        getLocation();
        return rootView;
    }

    private void initWebView(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        // mWebView.getSettings().setAppCacheEnabled(true);
        // mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //设置缩放工具显示
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        //设置支持获取位置权限
        mWebView.getSettings().setGeolocationEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

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
        mainView.initWebView(mWebView);
    }

    private void loadSchedule(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogHelper.e(LOG_TAG, ApiClient.SCHEDULE_URL + "?la=" + longitude + "&ln=" + latitude);
                mWebView.loadUrl(ApiClient.SCHEDULE_URL + "?la=" + longitude + "&ln=" + latitude);
            }
        }, 1000);
        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.clearHistory();
            }
        }, 2000);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocationManager.setLogType(LogType.GENERAL);
        getLocation();
        loadSchedule();
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(true)
                .setMinAccuracy(200.0f)
                .useOnlyGPServices(false)
                .askForGooglePlayServices(false)
                .doNotUseGooglePlayServices(true)
                .setWaitPeriod(ProviderType.GPS, 5 * 1000)
                .setWaitPeriod(ProviderType.NETWORK, 3 * 1000)
                .askForEnableGPS(false)
                .setRationalMessage("请求位置权限~");
    }

    @Override
    public void onLocationFailed(int failType) {
        dismissProgress();

        switch (failType){
            case FailType.PERMISSION_DENIED: {
                mainView.toastMessage("未授予位置权限,定位失败~");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                mainView.toastMessage("无法访问网络,定位失败~");
                break;
            }
            case FailType.TIMEOUT: {
                mainView.toastMessage("请求超时,定位失败~");
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        dismissProgress();
        if(latitude == 0.0 && longitude == 0.0){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            loadSchedule();
        }else {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                mWebView.loadUrl("javascript:+ change_zuobiao( " + latitude + "," + longitude + ",0)");
//                LogHelper.e(LOG_TAG,"ln" + latitude + "la" + longitude );
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //防止从设置页面回来，日程不能返回
        mainView.initWebView(mWebView);

        if(getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()){
            displayProgress();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissProgress();
    }

    private void displayProgress() {
//        if(progressDialog == null) {
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
//            progressDialog.setMessage("正在获取当前位置...");
//        }
//
//        if(!progressDialog.isShowing()) {
//            progressDialog.show();
//        }
    }

    private void dismissProgress(){
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
    }

}
