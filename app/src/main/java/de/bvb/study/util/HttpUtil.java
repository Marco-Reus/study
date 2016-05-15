package de.bvb.study.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关的工具类
 */
public class HttpUtil extends BaseUtil {

    /** 判断网络是否连接 */
    public static boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false;
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return null != info && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED;
    }

    /** 判断是否是wifi连接 */
    public static boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
