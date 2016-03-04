package cn.edu.twt.saishi_android.ui.file;

import java.io.File;

import cn.edu.twt.saishi_android.bean.FileUrl;

/**
 * Created by clifton on 16-2-28.
 */
public interface OnGetFileLoadedCallback {
    void onSuccess(File file, FileUrl fileUrl);

    void onFailure(String errorResponse);
}
