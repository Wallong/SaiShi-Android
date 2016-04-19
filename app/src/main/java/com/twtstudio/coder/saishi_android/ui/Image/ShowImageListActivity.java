package com.twtstudio.coder.saishi_android.ui.Image;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.support.ImageHelper;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.ui.Image.gestureimage.GestureImageView;
import com.twtstudio.coder.saishi_android.ui.Image.gestureimage.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by clifton on 16-4-16.
 */
public class ShowImageListActivity extends Activity {
    private static final String LOG_TAG = ShowImageListActivity.class.getSimpleName();

    @Bind(R.id.image_viewpager)
    MyViewPager viewPager;
    @Bind(R.id.image_text)
    TextView page;

    public static final String IMAGE_URLS = "image_urls";
    public static final String POSITION = "position";
    private String[] imageArray;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private int position;
    private GestureImageView[] mImageViews;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img_list);
        getIntentValue();
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(this))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        imageLoader.init(config);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        initViews();
        LogHelper.e(LOG_TAG, LOG_TAG + " is onCreate");
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        String urls = intent.getStringExtra(IMAGE_URLS);
        position = intent.getIntExtra(POSITION, 0);
        imageArray = urls.split(",");
        count = imageArray.length;
    }

    private void initViews() {
        if (count <= 1) {
            page.setVisibility(View.GONE);
        } else {
            page.setVisibility(View.VISIBLE);
            page.setText((position + 1) + "/" + count);
        }

        viewPager.setPageMargin(20);
        viewPager.setAdapter(new ImagePagerAdapter(getWebImageViews()));
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                page.setText((arg0 + 1) + "/" + count);
                mImageViews[arg0].reset();
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
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

    private List<View> getWebImageViews() {
        mImageViews = new GestureImageView[count];
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        List<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = layoutInflater.inflate(R.layout.fragment_image_item, null);
            final GestureImageView imageView = (GestureImageView) view
                    .findViewById(R.id.gesture_image);
            final ProgressBar progressBar = (ProgressBar) view
                    .findViewById(R.id.pb_image);
            mImageViews[i] = imageView;
            imageLoader.displayImage(imageArray[i], imageView, options,
                    new SimpleImageLoadingListener() {

                        @Override
                        public void onLoadingComplete(String imageUri,
                                                      View view, Bitmap loadedImage) {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                                    FailReason failReason) {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            progressBar.setVisibility(View.VISIBLE);
                        }

                    });
            imageView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    finish();
                }
            });
            views.add(view);
        }
        viewPager.setGestureImages(mImageViews);
        LogHelper.e(LOG_TAG, "views.size() is " + views.size());
        return views;
    }

    @Override
    protected void onDestroy() {
        if (mImageViews != null) {
            mImageViews = null;
        }
        imageLoader.clearMemoryCache();
        super.onDestroy();
    }

}
