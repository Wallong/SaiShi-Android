package cn.edu.twt.saishi_android.ui.file.download;

import java.util.List;

import cn.edu.twt.saishi_android.bean.DataItem;
import cn.edu.twt.saishi_android.ui.BaseActivity;
import cn.edu.twt.saishi_android.ui.main.list.OnGetDataItemsCallback;

/**
 * Created by clifton on 16-2-28.
 */
public class DownLoadActivity extends BaseActivity implements DownLoadView, OnGetDataItemsCallback {


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void toastMessage(String msg) {

    }

    @Override
    public void showFileContent() {

    }

    @Override
    protected List<Object> getModules() {
        return null;
    }

    @Override
    public void onSuccess(List<DataItem> datum) {

    }

    @Override
    public void onFailure(String errorString) {

    }
}
