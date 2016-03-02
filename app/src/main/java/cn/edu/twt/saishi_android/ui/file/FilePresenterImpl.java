package cn.edu.twt.saishi_android.ui.file;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.interactor.FileInteractor;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.MIMETypeUtils;

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
//    private int page = 1;

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
        this._fileInteractor.getFileItems(this);
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

        imageView = (ImageView)v.findViewById(R.id.iv_download);
        _fileView.showProgressBar();
        _fileView.downFile(position);
        LogHelper.v(LOG_TAG, "文件已经下载");


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
                LogHelper.v(LOG_TAG, "创建目录成功");
            }
        }
        return fileStorageDir;
    }

    @Override
    public void onSuccess(List<FileInfo> fileInfos) {
        this._fileView.stopRefresh();

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

    @Override
    public void onSuccess(File file) {
        _fileView.hideProgressBar();
        imageView.setImageResource(R.drawable.ic_downloaded);
        _fileView.startContentActivity(file);

        LogHelper.e(LOG_TAG, file.toString());

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
            System.out.println("打开失败");
        }

    }

    @Override
    public void onFailure(String errorString) {
        _fileView.hideProgressBar();
        _fileView.toastMessage(errorString);
    }
}
