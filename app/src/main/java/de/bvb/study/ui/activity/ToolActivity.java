package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import de.bvb.study.R;
import de.bvb.study.util.AdbWireUtil;
import de.bvb.study.util.CameraUtil;
import de.bvb.study.util.HttpUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ToolActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // 保持屏幕唤醒
        initView();

    }

    private void initView() {
        ToggleButton toggleButtonFlash = $(this, R.id.toggleButtonFlash);
        toggleButtonFlash.setText("打开手电筒");
        toggleButtonFlash.setTextOn("关闭手电筒");
        toggleButtonFlash.setTextOff("打开手电筒");
        toggleButtonFlash.setOnCheckedChangeListener(this);

        ToggleButton toggleButtonAdbWireless = $(this, R.id.toggleButtonAdbWireless);
        toggleButtonAdbWireless.setText("打开adb wireless");
        toggleButtonAdbWireless.setTextOn("关闭adb wireless ;\n请在 pc端执行 adb connect " + HttpUtil.getIPv4());
        toggleButtonAdbWireless.setTextOff("打开adb wireless");
        toggleButtonAdbWireless.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggleButtonAdbWireless:
                if (isChecked) {
                    AdbWireUtil.openAdbWireless();
                } else {
                    AdbWireUtil.closeAdbWireless();
                }
                break;
            case R.id.toggleButtonFlash:
                if (isChecked) {
                    CameraUtil.getInstance().openFlash();
                } else {
                    CameraUtil.getInstance().closeFlash();
                }
                break;
        }
    }

}
