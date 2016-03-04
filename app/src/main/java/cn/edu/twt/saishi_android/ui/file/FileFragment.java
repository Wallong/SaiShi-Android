package cn.edu.twt.saishi_android.ui.file;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.bean.FileInfo;
import cn.edu.twt.saishi_android.support.CacheDbHelper;
import cn.edu.twt.saishi_android.support.LogHelper;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.BaseFragment;
import cn.edu.twt.saishi_android.ui.common.OnItemClickListener;
import cn.edu.twt.saishi_android.ui.login.LoginActivity;
import cn.edu.twt.saishi_android.ui.main.MainActivity;

/**
 * Created by clifton on 16-2-28.
 */
public class FileFragment extends BaseFragment implements FileView, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener{
    public final static String LOG_TAG = FileFragment.class.getSimpleName();


    @Inject
    FilePresenter _filePresenter;
    @Bind(R.id.file_recycler_view)
    RecyclerView _recyclerView;
    @Bind(R.id.file_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;
    @Bind(R.id.pb_file)
    ProgressBar _progressBar;

    private FileAdapter _fileAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FileInfo fileInfo;

    public FileFragment(){

    }

    public static FileFragment getInstance(){
        FileFragment fileFragment = new FileFragment();
        return fileFragment;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new FileModule(this));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);
        ButterKnife.bind(this, rootView);

        _swipeRefreshLayout.setOnRefreshListener(this);

        _fileAdapter = new FileAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_fileAdapter);

        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition == linearLayoutManager.getItemCount() - 1 && dy > 0) {
                    _filePresenter.loadMoreFileItems();
                }
            }
        });

        _filePresenter.firstTimeLoadExploreItems();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClicked(View view, int position) {
        _filePresenter.onItemClicked(view, position);
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
        this.getActivity().finish();
    }

    @Override
    public void startRefresh() {
        if(!_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(true);
        }

    }

    @Override
    public void stopRefresh() {
        if(_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void updateListFile(List<FileInfo> items) {
        _fileAdapter.updateFile(items);

    }

    @Override
    public void addListFile(List<FileInfo> items) {
        _fileAdapter.addFile(items);
    }


    @Override
    public void downFile(int position){
        fileInfo = _fileAdapter.getItem(position);
        _filePresenter.downloadFile(fileInfo.file);
    }

    @Override
    public CacheDbHelper getCacheDbHelper() {
        return ((MainActivity)this.getActivity()).getDbHelper();
    }

    @Override
    public void showFooter() {
        _fileAdapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _fileAdapter.setUseFooter(false);
    }

    @Override
    public void showProgressBar() {
        _progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        _progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        _filePresenter.loadFileItems();
    }
}
