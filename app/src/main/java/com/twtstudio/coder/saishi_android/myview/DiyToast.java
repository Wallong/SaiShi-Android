package com.twtstudio.coder.saishi_android.myview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.twtstudio.coder.saishi_android.R;

/**
 * Created by clifton on 16-3-4.
 */
public class DiyToast {

    private static DiyToast diyToast;

    private Toast toast;

    private DiyToast(){
    }

    public static DiyToast createToastConfig(){
        if (diyToast ==null) {
            diyToast = new DiyToast();
        }
        return diyToast;
    }

    /**
     * 显示Toast
     * @param context
     * @param root
     * @param tag
     */

    public void ToastShow(Context context,ViewGroup root,int tag){
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_diy,root);
        ImageView mImageView = (ImageView) layout.findViewById(R.id.iv_toast);
        if(tag == 1) {
//            mImageView.setBackgroundResource(R.drawable.ic_modify_success);
        }else {
//            mImageView.setBackgroundResource(R.drawable.ic_modify_failure);
        }
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
