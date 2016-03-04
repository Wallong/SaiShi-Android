package cn.edu.twt.saishi_android.ui.main.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.SpacesItemDecoration;
import cn.edu.twt.saishi_android.ui.BaseFragment;
import cn.edu.twt.saishi_android.ui.common.OnItemClickListener;
import cn.edu.twt.saishi_android.ui.content.ContentActivity;
import cn.edu.twt.saishi_android.ui.login.LoginActivity;

/**
 * Created by clifton on 16-2-27.
 */
public class DataFragment extends BaseFragment implements DataListView, SwipeRefreshLayout.OnRefreshListener,OnItemClickListener {
    public final static String LOG_TAG = DataFragment.class.getSimpleName();
    public final static String PARAM_TYPE = "type";
    public static String type;

    @Inject
    DataPresenter _dataPresenter;
    @Bind(R.id.data_recycler_view)
    RecyclerView _recyclerView;
    @Bind(R.id.data_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;

    private DataAdapter _dataAdapter;
    private LinearLayoutManager linearLayoutManager;


    public DataFragment(){

    }


    public static DataFragment getInstance(String type){
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataFragment.PARAM_TYPE, type);
        dataFragment.setArguments(bundle);
        return dataFragment;
    }


    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DataModule(this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(PARAM_TYPE);
        LogHelper.v(LOG_TAG, "onCreate" + type);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogHelper.v(LOG_TAG, "onCreateView" + type);

        View rootView = inflater.inflate(R.layout.fragment_data_list, container, false);
        ButterKnife.bind(this, rootView);

        _swipeRefreshLayout.setOnRefreshListener(this);

        _dataAdapter = new DataAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_dataAdapter);

        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition == linearLayoutManager.getItemCount() - 1 && dy > 0) {
                    _dataPresenter.loadMoreDataItems(type);
                }
            }
        });
        int spacingInPixels = 1;
        _recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        _dataPresenter.firstTimeLoadExploreItems(type);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void startRefresh() {
        if(!_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if(!(_swipeRefreshLayout == null)) {
            if (_swipeRefreshLayout.isRefreshing()) {
                _swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListData(List<DataItem> items) {
        _dataAdapter.updateData(items);
    }

    @Override
    public void addListData(List<DataItem> items) {
        _dataAdapter.addDate(items);
    }

    @Override
    public void startContentActivity(int position) {
        LogHelper.v(LOG_TAG, "startContentActivity");
        DataItem dataItem = _dataAdapter.getItem(position);
        Intent intent = new Intent(this.getActivity(),ContentActivity.class);
        intent.putExtra("bean", dataItem);
        startActivity(intent);
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFooter() {
        _dataAdapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _dataAdapter.setUseFooter(false);
    }

    @Override
    public void onRefresh() {
        _dataPresenter.loadDataItems(type);
    }

    @Override
    public void onItemClicked(View view, int position) {
        LogHelper.v(LOG_TAG, "get position: "  + position);
        _dataPresenter.onItemClicked(view, position);
    }
    @Override
    public void notifySomething(){
        _dataAdapter.notifyDataSetChanged();
    }
}
