package cn.edu.twt.saishi_android.ui.file;

import java.io.File;

/**
 * Created by clifton on 16-2-28.
 */
public interface OnGetFileLoadedCallback {
    void onSuccess(File file);

    void onFailure(String errorResponse);
}
