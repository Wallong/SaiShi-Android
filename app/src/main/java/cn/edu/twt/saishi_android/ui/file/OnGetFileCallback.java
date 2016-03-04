package cn.edu.twt.saishi_android.ui.file;

import java.util.List;

import cn.edu.twt.saishi_android.bean.FileInfo;

/**
 * Created by clifton on 16-2-28.
 */
public interface OnGetFileCallback {
    void onSuccess(List<FileInfo> fileInfos, String responseString);

    void onFailure(String errorString);
}
