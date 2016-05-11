package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class FeedbackActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText(getClass().getSimpleName() + " ab  " + hashCode());
        setContentView(tv);
    }

}
