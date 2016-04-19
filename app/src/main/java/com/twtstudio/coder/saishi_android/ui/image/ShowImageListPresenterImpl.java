package com.twtstudio.coder.saishi_android.ui.image;

import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.twtstudio.coder.saishi_android.support.ImageLoaderManager;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by clifton on 16-4-20.
 */
public class ShowImageListPresenterImpl implements ShowImageListPresenter {
    private final static String LOG_TAG = ShowImageListPresenterImpl.class.getSimpleName();
    private ShowImageListView showImageListView;

    public ShowImageListPresenterImpl(ShowImageListView showImageListView){
        this.showImageListView = showImageListView;
    }

    @Override
    public void saveImage(final String url) {
        //获取图片在硬盘缓存上的路径
        final String path = getImageLoaderDiskCacheImageName(url, new HashCodeFileNameGenerator());
        LogHelper.e(LOG_TAG, path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //将图片从ImageView维护的硬盘缓存中取出，拷贝到我们指定的路径下
                File file = copyToUpLoadFileAndRename(path, StringUtils.cutString(url, 4));
                ((ShowImageListActivity)showImageListView).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //获取当前图片，这种常用于下载后上传，需要知道图片路径
                        String savePath = "已保存到" + ImageLoaderManager.FILE_SAVEPATH_UPLOAD;
                        showImageListView.toastMessage(savePath);
                        LogHelper.e(LOG_TAG, "uiThread------->" + Thread.currentThread().getId());
                    }
                });

                LogHelper.e(LOG_TAG, "new Thread------>" + Thread.currentThread().getId());
            }
        }).start();

    }

    /**
     * 获取ImageLoader的硬盘缓存中对应的路径
     * @param uriStr      图片资源uri
     * @param generator   给图片设置名字的generator，需要和config的config中设置的generator一样
     * @return
     */
    @Override
    public String getImageLoaderDiskCacheImageName(String uriStr, FileNameGenerator generator){
        String name = generator.generate(uriStr);
        String path = ImageLoaderManager.FILE_IMAGELOADER_CACHE + name;
        return path;
    }

    /**
     * 将图片从ImageLoader的硬盘缓存路径拷贝到上传路径，并指定新的文件名
     * 复制成功返回复制的文件
     * @param path     源路径
     * @param newName  我们指定的文件名
     */
    @Override
    public File copyToUpLoadFileAndRename(String path, String newName) {
        File src = new File(path);
        File des = new File(ImageLoaderManager.FILE_SAVEPATH_UPLOAD + newName);

        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = ImageLoaderManager.FILE_SAVEPATH_UPLOAD;
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        if(copyToAnotherDocument(src, des) != -1){
            return des;
        }else{
            return null;
        }
    }

    /**
     * 将一个文件拷贝到指定文件目录下，并修文件名
     * @param src
     * @param des
     */
    private int copyToAnotherDocument(File src, File des){
        //long time=new Date().getTime();
        int length=1024*10;
        try {
            FileInputStream in=new FileInputStream(src);
            FileOutputStream out=new FileOutputStream(des);
            byte[] buffer=new byte[length];
            while(true){
                int ins=in.read(buffer);
                if(ins==-1){
                    in.close();
                    out.flush();
                    out.close();
                    //return new Date().getTime()-time;
                    return 1;
                }else
                    out.write(buffer,0,ins);
            }
        }catch (Exception e){
        }finally {
        }
        return -1;
    }

}
