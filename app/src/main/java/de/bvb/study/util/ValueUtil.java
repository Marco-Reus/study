package de.bvb.study.util;

import android.graphics.drawable.Drawable;

/**
 * 获取 res/values/ 文件下的各种值
 */
public class ValueUtil extends BaseUtil {
    public static String getString(int id) {
        return resources.getString(id);
    }

    public static int getColor(int id) {
        return resources.getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return resources.getDrawable(id);
    }

    public static String[] getStringArray(int id) {
        return resources.getStringArray(id);
    }

    public static int getDimen(int id) {
        return resources.getDimensionPixelSize(id);
    }
}
