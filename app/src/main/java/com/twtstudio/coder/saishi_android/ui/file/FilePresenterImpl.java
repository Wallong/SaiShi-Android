package com.twtstudio.coder.saishi_android.ui.file;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;

import java.util.List;

import com.twtstudio.coder.saishi_android.bean.FileInfo;
import com.twtstudio.coder.saishi_android.interactor.FileInteractor;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;

/**
 * Created by clifton on 16-2-28.
 */
public class FilePresenterImpl implements FilePresenter, OnGetFileCallback {

    private final static String LOG_TAG = FilePresenterImpl.class.getSimpleName();

    private FileView _fileView;
    private FileInteractor _fileInteractor;

    private boolean isLoadMore = false;
    private boolean isFirstTimeLoad = true;

    private boolean isRefreshing = false;

    public FilePresenterImpl(FileView fileView, FileInteractor fileInteractor) {
        this._fileView = fileView;
        this._fileInteractor = fileInteractor;
    }

    @Override
    public void loadFileItems() {
        if(isRefreshing){
            return;
        }
        this._fileView.startRefresh();
        getFileItems();
    }

    private void getFileItems() {
        if(NetWorkHelper.isOnline()) {
            this._fileInteractor.getFileItems(this);
        }else{
            _fileView.toastMessage("网络未连接");
            this._fileView.stopRefresh();
        }
    }

    @Override
    public void loadMoreFileItems() {
        if(isLoadMore){
            return;
        }
        isLoadMore = true;
        _fileView.showFooter();
        getFileItems();
    }

    @Override
    public void firstTimeLoadExploreItems() {
        isRefreshing = true;
        _fileView.showFooter();
        getFileItems();
    }

    @Override
    public void onItemClicked(View v, int position) {
        _fileView.startFileContentActivity(position);
    }


    @Override
    public void onSuccess(List<FileInfo> fileInfos, String responseString) {
        this._fileView.stopRefresh();

        SQLiteDatabase db = _fileView.getCacheDbHelper().getWritableDatabase();
        db.execSQL("replace into CacheList(date,json) values(" + Integer.MAX_VALUE + ",' " + responseString + "')");
        db.close();

        handlItems(fileInfos);
    }

    @Override
    public void onFailure(String errorString) {
        if(errorString.equals("请重新登录")) {
            _fileView.startLoginActivity();
        }
        _fileView.hideProgressBar();
        _fileView.toastMessage(errorString);

    }

    private void handlItems(List<FileInfo> fileInfos){
        if(fileInfos != null){
            List<FileInfo> items = fileInfos;
            if(isLoadMore) {
                isLoadMore = false;
                _fileView.hideFooter();
                this._fileView.toastMessage("触底了～没有更多文件");
            } else {
                _fileView.updateListFile(items);
                if(isFirstTimeLoad){
                    _fileView.hideFooter();
                    isFirstTimeLoad = !isFirstTimeLoad;
                }
            }
        } else{
            _fileView.hideFooter();
            this._fileView.toastMessage("触底了～没有更多文件");
        }
        isLoadMore = false;
        isRefreshing = false;
    }
}
