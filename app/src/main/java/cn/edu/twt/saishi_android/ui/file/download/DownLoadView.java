package cn.edu.twt.saishi_android.ui.file.download;

/**
 * Created by clifton on 16-2-28.
 */
public interface DownLoadView {

    void showProgressBar();

    void hideProgressBar();

    void toastMessage(String msg);

    void showFileContent();
}
