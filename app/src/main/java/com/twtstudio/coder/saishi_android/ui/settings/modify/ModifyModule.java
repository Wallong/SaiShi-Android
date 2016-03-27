package com.twtstudio.coder.saishi_android.ui.settings.modify;

import javax.inject.Singleton;

import com.twtstudio.coder.saishi_android.AppModule;
import com.twtstudio.coder.saishi_android.interactor.ModifyInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-29.
 */
@Module(
        injects = {
                ModifyActivity.class
        },
        addsTo = AppModule.class,
        library = true
)

public class ModifyModule {

    private ModifyView mModifyView;

    public ModifyModule(ModifyView modifyView) {
        this.mModifyView = modifyView;
    }

    @Provides
    @Singleton
    public ModifyView provideModifyView() {
        return mModifyView;
    }

    @Provides
    @Singleton
    public ModifyPresenter provideModifyPresenter(ModifyView modifyView, ModifyInteractor modifyInteractor) {
        return new ModifyPresenterImpl(modifyView, modifyInteractor);
    }
}
