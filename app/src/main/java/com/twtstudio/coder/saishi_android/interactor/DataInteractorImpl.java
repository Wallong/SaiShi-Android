package com.twtstudio.coder.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import com.twtstudio.coder.saishi_android.api.ApiClient;
import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.bean.ImageInfo;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.main.list.OnAddViewsCallback;
import com.twtstudio.coder.saishi_android.ui.main.list.OnGetDataItemsCallback;
import cz.msebera.android.httpclient.Header;

/**
 * Created by clifton on 16-2-27.
 */
public class DataInteractorImpl implements DataInteractor {
    private static final String LOG_TAG = DataInteractorImpl.class.getSimpleName();

    @Override
    public void getDataItems(String type, int page, final OnGetDataItemsCallback onGetDataItemsCallback) {
        ApiClient.getData(type, page, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                boolean res = responseString.contains("result_code");
//                boolean zero = responseString.equals("[]");
                if(res){
                    onGetDataItemsCallback.onFailure("请重新登录");
                }else {
                    Gson gson = new Gson();
                    DataItem [] dataItems = null;
                    LogHelper.v(LOG_TAG, responseString.toString());
                    dataItems = gson.fromJson(responseString, DataItem[].class);
                    List<DataItem> datum = null;
                    if(!(dataItems == null)){
                        datum = Arrays.asList(dataItems);
                    }
                    if(datum != null && datum.size() != 0){
                        for (int i = (datum.get(0) == null ? 1 : 0); i < datum.size(); i++) {
                            LogHelper.e(LOG_TAG, "dataItem id -------->" + datum.get(i).getId());
                            ApiClient.getImage(datum.get(i).icon, new TextHttpResponseHandler() {
                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    onGetDataItemsCallback.onSuccess(null, null);
                                    LogHelper.e(LOG_TAG, "图片url获取失败");
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                    LogHelper.e(LOG_TAG, "图片url获取成功" + responseString);
                                    Gson gson1 = new Gson();
                                    if (responseString.contains("url")) {
                                        ImageInfo imageInfo = gson1.fromJson(responseString, ImageInfo.class);
                                        onGetDataItemsCallback.onSuccess(null, imageInfo);
                                    } else {
                                        onGetDataItemsCallback.onSuccess(null, null);
                                    }
                                }
                            });
                        }
                        LogHelper.e(LOG_TAG, datum == null ? "" : datum.toString());
                        onGetDataItemsCallback.onSuccess(datum, null);
                    }else {
                        onGetDataItemsCallback.onSuccess(datum, null);
                    }

                }
            }
        });
    }

    @Override
    public void addViews(String id, final OnAddViewsCallback onAddViewsCallback) {
        ApiClient.addViews(id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onAddViewsCallback.onFailure(responseString, null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                onAddViewsCallback.onSuccess(responseString);
            }
        });
    }
}
