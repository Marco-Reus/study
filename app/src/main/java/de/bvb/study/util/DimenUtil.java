package de.bvb.study.util;

import de.bvb.study.MyApplication;

public class DimenUtil {

    /** sp转换成px */
    public static int sp2px(float spValue) {
        float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /** px转换成sp */
    public static int px2sp(float pxValue) {
        float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /** dip转换成px */
    public static int dip2px(float dipValue) {
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    /** px转换成dip */
    public static int px2dip(float pxValue) {
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

}