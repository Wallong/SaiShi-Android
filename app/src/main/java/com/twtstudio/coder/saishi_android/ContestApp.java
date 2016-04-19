package com.twtstudio.coder.saishi_android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.twtstudio.coder.saishi_android.support.ImageLoaderManager;
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
        File cacheDir = new File(ImageLoaderManager.FILE_IMAGELOADER_CACHE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir)).build();
        ImageLoader.getInstance().init(config);
    }

    private static int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }

//    public static RefWatcher getRefWatcher() {
//        return instance.mRefWatcher;
//    }
}
