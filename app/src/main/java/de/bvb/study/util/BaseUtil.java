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
}
