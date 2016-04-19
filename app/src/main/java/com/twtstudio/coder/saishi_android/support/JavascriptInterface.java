package com.twtstudio.coder.saishi_android.support;

import android.content.Context;
import android.content.Intent;

import com.twtstudio.coder.saishi_android.ui.image.ShowImageListActivity;

/**
 * Created by clifton on 16-4-16.
 */
public class JavascriptInterface {

    private Context context;

    public JavascriptInterface(Context context) {
        LogHelper.e("JavascriptInterface---->", "JavascriptInterface is revolved");
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String object, int position) {
        LogHelper.e("JavascriptInterface---->", "openImage is revolved");
        Intent intent = new Intent();
        intent.putExtra(ShowImageListActivity.IMAGE_URLS, object);
        intent.putExtra(ShowImageListActivity.POSITION, position);
        intent.setClass(context, ShowImageListActivity.class);
        context.startActivity(intent);
    }

}
