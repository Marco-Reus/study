package de.bvb.study.util;


import android.util.Log;

import de.bvb.study.MyApplication;

public class LogUtil extends BaseUtil {
    private static final boolean isDebug = MyApplication.isDebug;

    private final static String APP_TAG = AppUtil.getAppName();

    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行<br>
     * at cn.utils.MainActivity.onCreate(MainActivity.java:17) 就是用來定位行的代碼<br>
     *
     * @return [ Thread:main, at
     * cn.utils.MainActivity.onCreate(MainActivity.java:17)]
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (StackTraceElement st : sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(LogUtil.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName()
                        + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]";
            }
        }
        return null;
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(APP_TAG, getMsgFormat(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, getMsgFormat(msg));
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.w(APP_TAG, getMsgFormat(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, getMsgFormat(msg));
        }
    }

    /** 输出格式定义 */
    private static String getMsgFormat(String msg) {
        return msg + " ;" + getFunctionName();
    }

}