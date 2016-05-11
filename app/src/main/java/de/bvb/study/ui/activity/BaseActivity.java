package de.bvb.study.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import cn.jpush.android.api.JPushInterface;
import de.bvb.study.MyApplication;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BaseActivity extends FragmentActivity {
    protected Context context;

    public <T extends View> T $(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.jPushEnable) JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MyApplication.jPushEnable) JPushInterface.onPause(this);
    }
}
