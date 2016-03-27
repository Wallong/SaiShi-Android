package com.twtstudio.coder.saishi_android.interactor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-21.
 */

@Module(
        complete = false,
        library = true
)
public class InteractorsModule {
    @Provides @Singleton
    public LoginInteractor provideLoginInteractor() {
        return new LoginInteractorImpl();
    }

    @Provides @Singleton
    public DataInteractor provideDataInteractor() {
        return new DataInteractorImpl();
    }

    @Provides @Singleton
    public FileInteractor provideFileInteractor() {
        return new FileInteractorImpl();
    }

    @Provides @Singleton
    public ModifyInteractor provideModifyInteractor() {
        return new ModifyInteractorImpl();
    }

    @Provides @Singleton
    public SettingsInteractor provideSettingsInteractor() {
        return new SettingsInteractorImpl();
    }

}
