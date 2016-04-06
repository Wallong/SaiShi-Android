package com.twtstudio.coder.saishi_android;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import im.fir.sdk.FIR;

/**
 * Created by clifton on 16-2-20.
 */
public class ContestApp extends Application {

    private static Context sContext;
    private static boolean sIsAppLunched;
    private ObjectGraph objectGraph;

    private static ContestApp instance;
//    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        FIR.addCustomizeValue("sdk", android.os.Build.VERSION.SDK_INT + "");
        FIR.addCustomizeValue("cpu", android.os.Build.CPU_ABI);
        FIR.addCustomizeValue("rom_provider", android.os.Build.MANUFACTURER);

        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);

        sContext = getApplicationContext();

        initImageLoader(sContext);

        instance = this;
//        mRefWatcher = LeakCanary.install(this);
    }

    public static ContestApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return sContext;
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
/*        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs()
                .build();*/
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(config);
    }

//    public static RefWatcher getRefWatcher() {
//        return instance.mRefWatcher;
//    }
}
