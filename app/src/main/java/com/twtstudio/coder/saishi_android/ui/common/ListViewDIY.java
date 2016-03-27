package com.twtstudio.coder.saishi_android.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by clifton on 16-3-25.
 */
public class ListViewDIY extends ListView {
    public ListViewDIY(Context context) {
        super(context);
    }
    public ListViewDIY(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public ListViewDIY(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
