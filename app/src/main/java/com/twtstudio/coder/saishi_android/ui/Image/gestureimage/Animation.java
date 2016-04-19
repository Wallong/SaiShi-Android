package com.twtstudio.coder.saishi_android.ui.Image.gestureimage;

/**
 * Created by clifton on 16-4-16.
 */
public interface Animation {

    /**
     * Transforms the view.
     * @param view
     * @param diffTime
     * @return true if this animation should remain active.  False otherwise.
     */
    boolean update(GestureImageView view, long time);

}
