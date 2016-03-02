package cn.edu.twt.saishi_android.ui.main.list;

import javax.inject.Singleton;

import cn.edu.twt.saishi_android.AppModule;
import cn.edu.twt.saishi_android.interactor.DataInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-27.
 */

@Module(
        injects = {
                DataFragment.class
        },
        addsTo = AppModule.class,
        library = true
)

public class DataModule {
    private DataListView _dataListView;

    public DataModule(DataListView _dataListView) {
        this._dataListView = _dataListView;
    }

    @Provides
    @Singleton
    public DataListView provideDataView(){
        return _dataListView;
    }

    @Provides @Singleton
    public DataPresenter provideDataPresenter(DataListView dataListView,DataInteractor dataInteractor){
        return new DataPresenterImpl(dataListView,dataInteractor);
    }
}
