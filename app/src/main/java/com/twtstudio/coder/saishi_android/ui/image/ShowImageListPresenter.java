package com.twtstudio.coder.saishi_android.ui.image;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;

import java.io.File;

/**
 * Created by clifton on 16-4-20.
 */
public interface ShowImageListPresenter {

    void saveImage(String url);


    String getImageLoaderDiskCacheImageName(String uriStr, FileNameGenerator generator);

    File copyToUpLoadFileAndRename(String path, String newName);

}
