package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.FileInfo;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.file.OnGetFileCallback;
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

}