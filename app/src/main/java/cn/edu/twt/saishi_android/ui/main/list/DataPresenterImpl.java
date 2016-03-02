package cn.edu.twt.saishi_android.ui.main.list;

import android.view.View;

import java.util.List;

import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.interactor.DataInteractor;
import cn.edu.twt.saishi_android.support.LogHelper;

/**
 * Created by clifton on 16-2-14.
 */
public class DataPresenterImpl implements  DataPresenter, OnGetDataItemsCallback{

    private final static String LOG_TAG = DataPresenterImpl.class.getSimpleName();

    private DataListView _dataListView;
    private DataInteractor _dataInteractor;

    private boolean isLoadMore = false;
    private boolean isFirstTimeLoad = true;
    private int page = 1;

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
        LogHelper.e(LOG_TAG, ""+position);
        _dataListView.startContentActivity(position);
    }

    @Override
    public void onSuccess(List<DataItem> datum) {


        this._dataListView.stopRefresh();

        if(datum != null){
            List<DataItem> items = datum;
            if(isLoadMore) {
                _dataListView.addListData(items);
                isLoadMore = false;
            } else {
                _dataListView.updateListData(items);
                if(isFirstTimeLoad){
                    _dataListView.hideFooter();
                    isFirstTimeLoad = !isFirstTimeLoad;
                }
            }
        } else {
            _dataListView.hideFooter();
            this._dataListView.toastMessage("触底了～没有更多信息");
        }
        isLoadMore = false;
        isRefreshing = false;
    }

    @Override
    public void onFailure(String errorString) {
        if(errorString.equals("请登录")) {
            _dataListView.startLoginActivity();
        }
        page -= 1;
        this._dataListView.stopRefresh();
        this._dataListView.toastMessage(errorString);
        isLoadMore = false;
        isRefreshing = false;
    }
}
