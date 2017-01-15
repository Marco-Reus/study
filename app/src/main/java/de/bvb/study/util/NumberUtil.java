package de.bvb.study.util;

/**
 * Created by Administrator on 2016/5/21.
 */
public class NumberUtil {
    public static boolean isInRange(int data, int min, int max) {
        return data <= max && data >= min;
    }

    public static int setInRange(int data, int min, int max) {
        if (data > max) return max;
        if (data < min) return min;
        return data;
    }
}
