package com.twtstudio.coder.saishi_android.ui.Image;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by clifton on 16-4-16.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private List<View> views;

    public ImagePagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
