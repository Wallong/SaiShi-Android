package com.twtstudio.coder.saishi_android.ui.common;

import com.twtstudio.coder.saishi_android.bean.ImageInfo;
import com.twtstudio.coder.saishi_android.support.LogHelper;

/**
 * Created by clifton on 16-3-2.
 */
public class ImageProvider implements OnGetImageCallback {

    private final static String LOG_TAG = ImageProvider.class.getSimpleName();

    public String imageUrl = null;


    public String getImageUrl(String iconId) {

        getImage(iconId);

        return imageUrl;
    }

    public void getImage(String iconId){
        ImageHelper.downLoadImage(iconId, this);
    }

    @Override
    public void onSuccess(ImageInfo imageInfo) {
        imageUrl = imageInfo.url;
        LogHelper.e(LOG_TAG, "获得图片的url");
    }

    @Override
    public void onFailure(String errorString) {

    }


}
