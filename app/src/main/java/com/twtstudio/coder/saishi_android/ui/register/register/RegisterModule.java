package com.twtstudio.coder.saishi_android.ui.register.register;

import com.twtstudio.coder.saishi_android.AppModule;
import com.twtstudio.coder.saishi_android.interactor.RegisterInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-4-4.
 */
@Module(
        injects = {
                RegisterFragment.class
        },
        addsTo = AppModule.class,
        library = true
)

public class RegisterModule {
    private RegisterView _registerView;

    public RegisterModule(RegisterView _registerView) {
        this._registerView = _registerView;
    }

    @Provides
    @Singleton
    public RegisterView provideRegisterView(){
        return _registerView;
    }

    @Provides @Singleton
    public RegisterPresenter provideRegisterPresenter(RegisterView registerView, RegisterInteractor registerInteractor){
        return new RegisterPresenterImpl(registerView,registerInteractor);
    }
}
