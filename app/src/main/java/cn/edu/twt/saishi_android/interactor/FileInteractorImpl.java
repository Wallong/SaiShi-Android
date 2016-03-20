package cn.edu.twt.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.bean.FileUrl;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.ui.file.OnGetFileCallback;
import cn.edu.twt.saishi_android.ui.file.OnGetFileLoadedCallback;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-2-28.
 */
public class FileInteractorImpl implements FileInteractor {
    private static final String LOG_TAG = FileInteractorImpl.class.getSimpleName();

    @Override
    public void getFileItems(final OnGetFileCallback onGetFileCallback) {
        ApiClient.getFile(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogHelper.v(LOG_TAG, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                boolean res = responseString.contains("result_code");
                if(res){
                    onGetFileCallback.onFailure("请重新登录");
                }else {
                    Gson gson = new Gson();
                    LogHelper.e(LOG_TAG, responseString);
                    FileInfo[] items = gson.fromJson(responseString, FileInfo[].class);
                    List<FileInfo> fileInfos = Arrays.asList(items);
                    LogHelper.e(LOG_TAG, fileInfos.toString());
                    onGetFileCallback.onSuccess(fileInfos, responseString);
                }
            }
        });
    }

    @Override
    public void beginDownloadFile(String fileId, final File filePath, final OnGetFileLoadedCallback onGetFileLoadedCallback) {
        ApiClient.downloadFile(fileId, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                LogHelper.e(LOG_TAG, responseString);
                FileUrl fileUrl = gson.fromJson(responseString, FileUrl.class);
                onGetFileLoadedCallback.onSuccess(null, fileUrl);

                ApiClient.haveDownloadFile(fileUrl.url, new FileAsyncHttpResponseHandler(filePath) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        LogHelper.e(LOG_TAG, "下载成功");
                        onGetFileLoadedCallback.onSuccess(file, null);
                    }
                });

            }
        });

    }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
//                onGetFileLoadedCallback.onFailure("下载失败");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, File file) {
//                if(statusCode==200){
//                    onGetFileLoadedCallback.onSuccess(file);
//                }
//            }
//        });

}