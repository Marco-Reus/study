package de.bvb.study.util;

import java.util.List;

/**
 * Created by Administrator on 2016/5/14.
 */
public class ListUtil extends BaseUtil {
    public static boolean isEmpty(List list) {
        return null == list || list.size() <= 0;
    }

    public static int getLength(List list) {
        return null == list ? 0 : list.size();
    }
}
