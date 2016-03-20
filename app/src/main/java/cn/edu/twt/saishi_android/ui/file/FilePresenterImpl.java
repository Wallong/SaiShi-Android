package cn.edu.twt.saishi_android.ui.file;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.bean.FileUrl;
import cn.edu.twt.saishi_android.interactor.FileInteractor;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.MIMETypeUtils;
import cn.edu.twt.saishi_android.support.NetWorkHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.support.StringUtils;

/**
 * Created by clifton on 16-2-28.
 */
public class FilePresenterImpl implements FilePresenter, OnGetFileCallback, OnGetFileLoadedCallback {

    private final static String LOG_TAG = FilePresenterImpl.class.getSimpleName();

    private FileView _fileView;
    private FileInteractor _fileInteractor;
    private ImageView imageView;
    private TextView tv_Type;
    private TextView tv_Time;
    private TextView tv_Title;

    private boolean isLoadMore = false;
    private boolean isFirstTimeLoad = true;
    private String fileId;
    private FileInfo fileInfo;

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
//        SQLiteDatabase db = _fileView.getCacheDbHelper().getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from CacheList where date = " + Integer.MAX_VALUE, null);
//        if (cursor.moveToFirst()) {
//            String json = cursor.getString(cursor.getColumnIndex("json"));
//            Gson gson = new Gson();
//            FileInfo[] items = gson.fromJson(json, FileInfo[].class);
//            List<FileInfo> fileInfos = Arrays.asList(items);
//
//            handlItems(fileInfos);
//        }
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
    public void downloadFile(String fileId) {
        _fileInteractor.beginDownloadFile(fileId, makedirs(), this);
    }

    @Override
    public File makedirs() {
        File fileStorageDir = new File(Environment.getExternalStorageDirectory() + File.separator + "ContestApp");
        if(! fileStorageDir.exists()){
            if(!fileStorageDir.mkdirs()){
                LogHelper.v(LOG_TAG, "创建目录失败");
                return null;
            } else{
                LogHelper.v(LOG_TAG, "创建目录成功" + fileStorageDir.toString());
            }
        }
        return fileStorageDir;
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
    public void onSuccess(File file,FileUrl fileUrl) {
        if(!(file == null)) {
            _fileView.hideProgressBar();
            imageView.setImageResource(R.drawable.ic_downloaded);

            openFile(file);
        }
    }

    @Override
    public void onFailure(String errorString) {
        if(errorString.equals("请重新登录")) {
            _fileView.startLoginActivity();
        }
        _fileView.hideProgressBar();
        _fileView.toastMessage(errorString);

    }

    private void openFile(File file){
        Uri path1 = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //设置intent的Action属性
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //获取文件file的MIME类型
        String type = MIMETypeUtils.getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(path1, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            _fileView.startActivity(intent);
        }
        catch (ActivityNotFoundException e) {
            _fileView.toastMessage("没有可以打开该文件的应用");
        }
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
