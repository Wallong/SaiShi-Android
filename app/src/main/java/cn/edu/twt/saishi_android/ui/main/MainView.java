package cn.edu.twt.saishi_android.ui.main;

/**
 * Created by clifton on 16-2-27.
 */
public interface MainView {

    void replaceFragment(String type);

    void closeMenu();

    void startScheduleActivity();

    void startSettingsActivity();

    void setToolbar(String title);

    void toastMessage(String msg);

}
