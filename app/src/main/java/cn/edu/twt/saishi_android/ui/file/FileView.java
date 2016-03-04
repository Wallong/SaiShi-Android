package cn.edu.twt.saishi_android.ui.file;

import android.content.Intent;

import java.io.File;
import java.util.List;

import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.support.CacheDbHelper;

/**
 * Created by clifton on 16-2-27.
 */
public interface FileView {

    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListFile(List<FileInfo> items);

    void addListFile(List<FileInfo> items);

    void startActivity(Intent intent);

    void showFooter();

    void hideFooter();

    void showProgressBar();

    void hideProgressBar();

    void downFile(int positon);

    void startLoginActivity();

    CacheDbHelper getCacheDbHelper();
}
