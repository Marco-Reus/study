package de.bvb.study;

import android.app.Application;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static MyApplication getContext() {
        return instance;
    }
}
