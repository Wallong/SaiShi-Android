package com.twtstudio.coder.saishi_android.ui.login;

import javax.inject.Singleton;

import com.twtstudio.coder.saishi_android.AppModule;
import com.twtstudio.coder.saishi_android.interactor.LoginInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-19.
 */

@Module(
        injects = LoginActivity.class,
        addsTo = AppModule.class
)

public class LoginModule {

    private LoginView mLoginView;

    public LoginModule(LoginView loginView) {
        this.mLoginView = loginView;
    }

    @Provides
    @Singleton
    public LoginView provideLoginView() {
        return mLoginView;
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        return new LoginPresenterImpl(loginView, loginInteractor);
    }
}
