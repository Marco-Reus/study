package de.bvb.study.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ActivityUtil extends BaseUtil {
    public static void startActivity(Context context, Class<?> interfaceClassName) {
        if (context != null && interfaceClassName != null)
            context.startActivity(new Intent(context, interfaceClassName));
    }

}
