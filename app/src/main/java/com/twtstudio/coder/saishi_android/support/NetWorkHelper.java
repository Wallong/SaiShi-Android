package com.twtstudio.coder.saishi_android.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.twtstudio.coder.saishi_android.ContestApp;

/**
 * Created by clifton on 16-2-21.
 */
public class NetWorkHelper {
    public static boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                ContestApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
