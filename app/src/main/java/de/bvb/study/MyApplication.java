package de.bvb.study;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;
import de.bvb.study.util.CrashHandlerUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static boolean isDebug = true;

    // 是否使用极光推送推送消息
    public static final boolean jPushEnable = true;
    // 设置开启日志,发布时请关闭日志
    public static final boolean jPushDebugMode = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化 CrashHandle
        if (!isDebug) CrashHandlerUtil.getInstance().init(this); //uat模式下不收集日志

        // 极光推送初始化
        if (jPushEnable) {
            JPushInterface.setDebugMode(jPushDebugMode);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
        }


    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static MyApplication getContext() {
        return instance;
    }
}
