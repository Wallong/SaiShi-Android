package cn.edu.twt.saishi_android.ui.common;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.ImageInfo;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-3-2.
 */
public class ImageHelper {

    public static void downLoadImage(String icon, final OnGetImageCallback onGetImageCallback){
        ApiClient.getImage(icon, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onGetImageCallback.onFailure(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                if(responseString.contains("url")) {
                    ImageInfo imageInfo = gson.fromJson(responseString, ImageInfo.class);
                    onGetImageCallback.onSuccess(imageInfo);
                }else {
                    onGetImageCallback.onFailure(responseString);
                }
            }
        });
    }

    public static ImageLoader getImageLoder(){

        ImageLoader imageloader = ImageLoader.getInstance();

        return imageloader;

    }

    public static DisplayImageOptions getDisplayImageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        return options;
    }
}
