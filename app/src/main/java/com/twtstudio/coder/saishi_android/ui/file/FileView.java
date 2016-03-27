package com.twtstudio.coder.saishi_android.ui.file;

import android.content.Intent;

import java.util.List;

import com.twtstudio.coder.saishi_android.bean.FileInfo;
import com.twtstudio.coder.saishi_android.support.CacheDbHelper;

/**
 * Created by clifton on 16-2-27.
 */
public interface FileView {

    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListFile(List<FileInfo> items);

    void startActivity(Intent intent);

    void showFooter();

    void hideFooter();

    void showProgressBar();

    void hideProgressBar();

    void startFileContentActivity(int position);

    void startLoginActivity();

    CacheDbHelper getCacheDbHelper();
}
