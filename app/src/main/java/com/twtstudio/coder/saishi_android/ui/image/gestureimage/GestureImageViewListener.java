package com.twtstudio.coder.saishi_android.ui.image.gestureimage;

/**
 * Created by clifton on 16-4-16.
 */
public interface GestureImageViewListener {

    void onTouch(float x, float y);

    void onScale(float scale);

    void onPosition(float x, float y);

}
