package cn.edu.twt.saishi_android.ui.main.list;

/**
 * Created by clifton on 16-3-10.
 */
public interface OnAddViewsCallback {

    void onSuccess(String responseString);

    void onFailure(String errorString, String tag);
}
