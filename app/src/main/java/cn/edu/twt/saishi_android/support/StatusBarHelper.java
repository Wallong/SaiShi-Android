package cn.edu.twt.saishi_android.support;

import android.app.Activity;
import android.os.Build;

import cn.edu.twt.saishi_android.R;

/**
 * Created by clifton on 16-2-21.
 */
public class StatusBarHelper {
    public static void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }
}
