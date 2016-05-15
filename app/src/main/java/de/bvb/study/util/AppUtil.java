package de.bvb.study.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import de.bvb.study.MyApplication;

/**
 * 跟App相关的辅助类
 */
public class AppUtil extends BaseUtil {

    /** 获取应用程序名称 */
    public static String getAppName() {
        try {
            PackageManager packageManager = MyApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return ValueUtil.getString(packageInfo.applicationInfo.labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取应用程序版本名称信息 */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
