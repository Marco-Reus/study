package de.bvb.study.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/5/21.
 */
public class ViewUtil {

    public static <T extends View> T $(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }
}
