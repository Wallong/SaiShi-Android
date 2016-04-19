package com.twtstudio.coder.saishi_android.ui.main.list;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.twtstudio.coder.saishi_android.bean.DataItem;
import com.twtstudio.coder.saishi_android.bean.ImageInfo;
import com.twtstudio.coder.saishi_android.interactor.DataInteractor;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.NetWorkHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;

/**
 * Created by clifton on 16-2-14.
 */
public class DataPresenterImpl implements  DataPresenter, OnGetDataItemsCallback, OnAddViewsCallback{

    private final static String LOG_TAG = DataPresenterImpl.class.getSimpleName();

    private DataListView _dataListView;
    private DataInteractor _dataInteractor;

    private ArrayList<ImageInfo> imageInfos = new ArrayList<>();
    private List<DataItem> dataItems = null;
    private boolean isLoadMore = false;
    private boolean isFirstTimeLoad = true;
    private int page = 1;
    private static int total = 0;


    private boolean isRefreshing = false;

    public DataPresenterImpl(DataListView dataListView, DataInteractor dataInteractor) {
        this._dataListView = dataListView;
        this._dataInteractor = dataInteractor;
    }

    @Override
    public void loadDataItems(String type) {
        if(isRefreshing){
            return;
        }
        this._dataListView.startRefresh();
        page = 1;
        getDataItems(type);

    }

    private void getDataItems(String type) {
        if(!NetWorkHelper.isOnline()){
            _dataListView.toastMessage("网络未连接");
            this._dataListView.stopRefresh();
        }
        total = 0;
        this._dataInteractor.getDataItems(type, page, this);
    }

    @Override
    public void loadMoreDataItems(String type) {
        if(isLoadMore){
            return;
        }
        page += 1;
        isLoadMore = true;
        _dataListView.showFooter();
        getDataItems(type);
    }

    @Override
    public void firstTimeLoadExploreItems(String type) {
        isRefreshing = true;
        page = 1;
        _dataListView.showFooter();
        getDataItems(type);
    }

    @Override
    public void onItemClicked(View v, int position) {
        LogHelper.e(LOG_TAG, "" + position);
        _dataListView.startContentActivity(position);
    }

    @Override
    public void addViews(String id) {
        _dataInteractor.addViews(id, this);
    }

    //加载消息条目
    @Override
    public void onSuccess(List<DataItem> datum, ImageInfo imageInfo) {
        total ++;
        if(datum != null){
            dataItems = datum;
            LogHelper.e(LOG_TAG, "dataItems.size()-------->" + dataItems.size());
        }else if(imageInfo != null){
            imageInfos.add(imageInfo);
        }
        //判断是否全部条目均获得url
        if((dataItems != null) && dataItems.size()!=0 &&(dataItems.get(0)==null?(total == dataItems.size()):(total==dataItems.size()+1))){
            for(int i = (dataItems.get(0)==null?1:0); i < dataItems.size(); i++){
                for(int j = 0; j < imageInfos.size(); j++){
                    if(dataItems.get(i).icon != null && (dataItems.get(i).icon.equals(imageInfos.get(j).id))){
                        dataItems.get(i).setUrl(imageInfos.get(j).url);
                    }
                }
            }
            this._dataListView.stopRefresh();
            if(isLoadMore) {
                _dataListView.addListData(dataItems);
                LogHelper.e(LOG_TAG, "这里在执行loadmore操作");

                isLoadMore = false;
            } else {
                _dataListView.updateListData(dataItems);
                if(isFirstTimeLoad){
                    _dataListView.hideFooter();
                    isFirstTimeLoad = !isFirstTimeLoad;
                }
                isLoadMore = false;
            }

            imageInfos.clear();
            dataItems = null;
            total = 0;
        }else if(dataItems != null && dataItems.size() == 0){
            isLoadMore = false;
            _dataListView.hideFooter();
            this._dataListView.stopRefresh();
            this._dataListView.toastMessage("触底了～没有更多信息");
            total = 0;
        }
        isRefreshing = false;

    }

    @Override
    public void onFailure(String errorString) {
        if(errorString.equals("请重新登录")) {
            _dataListView.startLoginActivity();
            PrefUtils.setLogin(false);
        }
        page -= 1;
        this._dataListView.stopRefresh();
        this._dataListView.toastMessage(errorString);
        isLoadMore = false;
        isRefreshing = false;

    }

    //增加访问量
    @Override
    public void onSuccess(String responseString) {
        LogHelper.e(LOG_TAG, "onSuccess" + responseString);
    }

    @Override
    public void onFailure(String errorString, String tag) {
        LogHelper.e(LOG_TAG, "onFailure" + errorString);
    }
}
