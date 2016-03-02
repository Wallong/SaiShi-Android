package cn.edu.twt.saishi_android.ui.login;

/**
 * Created by clifton on 16-2-19.
 */
public interface LoginView {

    void usernameError(String errorString);

    void passwordError(String errorString);

    void showProgressBar();

    void hideProgressBar();

    void hideKeyboard();

    void toastMessage(String msg);

    void startMainActivity();
}
