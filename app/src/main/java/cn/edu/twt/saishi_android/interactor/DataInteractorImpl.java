package cn.edu.twt.saishi_android.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import cn.edu.twt.saishi_android.api.ApiClient;
import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.ui.main.list.OnGetDataItemsCallback;
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
                LogHelper.v(LOG_TAG, responseString.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                DataItem [] dataItems = null;
                LogHelper.v(LOG_TAG, responseString.toString());
                boolean res = responseString.contains("result_code");
                if(res){
                    onGetDataItemsCallback.onFailure("请登录");
                }else{
                    dataItems = gson.fromJson(responseString, DataItem[].class);
                    List<DataItem> datum = null;
                    if(!(dataItems == null)){
                        datum = Arrays.asList(dataItems);
                    }
                    LogHelper.v(LOG_TAG, datum==null?"":datum.toString());
                    onGetDataItemsCallback.onSuccess(datum);
                }
            }
        });
    }
}
