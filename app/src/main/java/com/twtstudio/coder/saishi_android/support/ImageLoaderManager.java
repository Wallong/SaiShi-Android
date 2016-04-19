package com.twtstudio.coder.saishi_android.support;

import android.os.Environment;

/**
 * Created by clifton on 16-4-20.
 */
public class ImageLoaderManager {
    //ImageView 的文件缓存
    public final static String FILE_IMAGELOADER_CACHE = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/SaiShi/cache/";

    //使用ImageLoader下载后需要上传的图片，单独拷贝到这个目录下，并将文件名修改为相应的格式
    public final static String FILE_SAVEPATH_UPLOAD = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Pictures/辅导员赛事";
}
