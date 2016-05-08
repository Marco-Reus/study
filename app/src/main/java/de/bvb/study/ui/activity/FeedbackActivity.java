package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class FeedbackActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TextView tv = new TextView(this);
        tv.setText(getClass().getSimpleName() + " ab  " + hashCode());
        setContentView(tv);
    }
}
