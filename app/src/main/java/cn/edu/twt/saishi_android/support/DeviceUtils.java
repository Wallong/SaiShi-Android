package cn.edu.twt.saishi_android.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.File;

import cn.edu.twt.saishi_android.BuildConfig;
import cn.edu.twt.saishi_android.ContestApp;

/**
 * Created by clifton on 16-2-20.
 */
public class DeviceUtils {

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSource() {
        return getBrand() + "" + getModel();
    }

    public static String getNetworkType() {
        NetworkInfo info = ((ConnectivityManager) ContestApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info != null) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return "WIFI";
                case ConnectivityManager.TYPE_MOBILE:
                    return "GPRS";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    public static boolean isRooted() {
        boolean isRooted;

        try {
            isRooted = !((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists()));
        } catch (Exception e) {
            return false;
        }

        return isRooted;
    }

}
