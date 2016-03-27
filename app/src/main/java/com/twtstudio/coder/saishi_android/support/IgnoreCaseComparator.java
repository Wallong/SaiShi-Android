package com.twtstudio.coder.saishi_android.support;

import java.util.Comparator;

import com.twtstudio.coder.saishi_android.bean.DataItem;

/**
 * Created by clifton on 16-3-25.
 */
public class IgnoreCaseComparator implements Comparator<DataItem> {
    @Override
    public int compare(DataItem lhs, DataItem rhs) {
        if (lhs.getPaixu() != null || rhs.getPaixu() != null) {
            return lhs.getPaixu().compareToIgnoreCase(rhs.getPaixu());
        }else {
            return 0;
        }
    }

}
