package cn.edu.twt.saishi_android.interactor;

import cn.edu.twt.saishi_android.ui.file.OnGetFileCallback;

/**
 * Created by clifton on 16-2-28.
 */
public interface FileInteractor {
    void getFileItems(OnGetFileCallback onGetFileCallback);

//    void beginDownloadFile(String fileId, File filePath, OnGetFileLoadedCallback onGetFileLoadedCallback);
}
