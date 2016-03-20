package cn.edu.twt.saishi_android.ui.settings;

import javax.inject.Singleton;

import cn.edu.twt.saishi_android.AppModule;
import cn.edu.twt.saishi_android.interactor.SettingsInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-28.
 */

@Module(
        injects = SettingsActivity.class,
        addsTo = AppModule.class
)

public class SettingsModule {

    private SettingsView mSettingsView;

    public SettingsModule(SettingsView settingsView) {
        this.mSettingsView = settingsView;
    }

    @Provides
    @Singleton
    public SettingsView provideSettingsView() {
        return mSettingsView;
    }

    @Provides
    @Singleton
    public SettingsPresenter provideSettingsPresenter(SettingsView settingsView, SettingsInteractor settingsInteractor) {
        return new SettingsPresenterImpl(settingsView, settingsInteractor);
    }
}
