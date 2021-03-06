package com.twtstudio.coder.saishi_android;

import com.twtstudio.coder.saishi_android.interactor.InteractorsModule;
import dagger.Module;

/**
 * Created by clifton on 16-2-21.
 */
@Module(
        includes = {
                InteractorsModule.class
        },
        injects = {
                ContestApp.class
        }
)
public class AppModule {

    private ContestApp app;

    public AppModule(ContestApp app) {
        this.app = app;
    }

}
