package cn.edu.twt.saishi_android.ui.file;

import javax.inject.Singleton;

import cn.edu.twt.saishi_android.AppModule;
import cn.edu.twt.saishi_android.interactor.FileInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Created by clifton on 16-2-28.
 */
@Module(
        injects = {
                FileFragment.class
        },
        addsTo = AppModule.class,
        library = true
)

public class FileModule {
    private FileView _fileView;

    public FileModule(FileView _fileView) {
        this._fileView = _fileView;
    }

    @Provides
    @Singleton
    public FileView provideFileView(){
        return _fileView;
    }

    @Provides @Singleton
    public FilePresenter provideFilePresenter(FileView fileListView,FileInteractor fileInteractor){
        return new FilePresenterImpl(fileListView,fileInteractor);
    }
}
