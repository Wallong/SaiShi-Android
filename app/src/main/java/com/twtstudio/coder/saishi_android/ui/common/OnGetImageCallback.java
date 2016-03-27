package com.twtstudio.coder.saishi_android.ui.common;

import com.twtstudio.coder.saishi_android.bean.ImageInfo;

/**
 * Created by clifton on 16-3-2.
 */
public interface OnGetImageCallback {

    void onSuccess(ImageInfo imageInfo);

    void onFailure(String errorString);
}
