package de.bvb.study.util;

import android.content.Context;
import android.content.res.Resources;

import de.bvb.study.MyApplication;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BaseUtil {
    protected static Context context = MyApplication.getContext();
    protected static Resources resources = context.getResources();

    /**
     * 禁止子类被 new 出来
     */
    protected BaseUtil() { throw new UnsupportedOperationException(getClass().getSimpleName() + " .class cannot be instantiated");}

    protected static Object getSystemService(String serviceName) {
        return context.getSystemService(serviceName);
    }
}
