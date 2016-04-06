package com.twtstudio.coder.saishi_android.ui.schedule;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.support.ExitApplication;
import com.twtstudio.coder.saishi_android.support.LocationService;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.main.MainView;

/**
 * Created by clifton on 16-2-27.
 */
public class ScheduleFragment extends Fragment implements LocationListener {
    private static final String LOG_TAG = ScheduleFragment.class.getSimpleName();
    //The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    //The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    private final static boolean forceNetwork = false;

    private static LocationService instance = null;

    @Bind(R.id.wv_schedule)
    WebView mWebView;

    private MainView mainView;
    private LocationManager locationManager;
    private Location location;

    private double longitude;
    private double latitude;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean locationServiceAvailable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, rootView);
        mainView = (MainView)getActivity();
        initWebView();
        return rootView;
    }

    private void initWebView(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
//        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
//        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
//            super.onGeolocationPermissionsShowPrompt(origin, callback);
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
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                LogHelper.e(LOG_TAG, ApiClient.SCHEDULE_URL + "?la=" + longitude + "&ln=" + latitude);
                mWebView.loadUrl(ApiClient.SCHEDULE_URL + "?la=" + longitude + "&ln=" + latitude);
            }
        });
        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.clearHistory();
            }
        }, 1000);
    }

    @TargetApi(23)
    private void initialLocation()  {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        try   {
            this.longitude = 0.0;
            this.latitude = 0.0;
            this.locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

            // Get GPS and network status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (forceNetwork) isGPSEnabled = false;

            LogHelper.e(LOG_TAG, "isGPSEnabled------>" + isGPSEnabled + "   isNetworkEnabled------>" + isNetworkEnabled);


            if (!isNetworkEnabled && !isGPSEnabled) {
                // cannot get location
                this.locationServiceAvailable = false;
            } else {
                this.locationServiceAvailable = true;

                if (isGPSEnabled)  {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null)  {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }else{
                    }
                    LogHelper.e(LOG_TAG,"GPS" + location.getLatitude() + "    " + location.getLongitude());
                }


                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    LogHelper.e(LOG_TAG, "isNetworkEnabled" + "locationManager is not null 2" + locationManager == null);
                    if (locationManager != null)   {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }else{
                        LogHelper.e(LOG_TAG, "isNetworkEnabled" +"locationManager is null");
                    }
                }
            }
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    private void removeLocation() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        try {
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadSchedule();
        initialLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        //防止从设置页面回来，日程不能返回
        mainView.initWebView(mWebView);
    }

    @Override
    public void onPause() {
        super.onPause();
        removeLocation();
    }


    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
//        mainView.toastMessage("la=" + longitude + "ln=" + latitude);
        LogHelper.e(LOG_TAG, location.getLatitude() + "    " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
