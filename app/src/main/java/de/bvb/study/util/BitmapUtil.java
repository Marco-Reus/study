package de.bvb.study.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2016/5/21.
 */
public class BitmapUtil extends BaseUtil {
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(resources, resId);
    }
}
