package cn.edu.twt.saishi_android.ui.common;

import cn.edu.twt.saishi_android.bean.ImageInfo;

/**
 * Created by clifton on 16-3-2.
 */
public interface OnGetImageCallback {

    void onSuccess(ImageInfo imageInfo);

    void onFailure(String errorString);
}
