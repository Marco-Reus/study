package de.bvb.study.ui.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import de.bvb.study.R;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ToolActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private ToggleButton toggleButtonFlash;

    private Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // 保持屏幕唤醒
        initView();
    }

    private void initView() {
        toggleButtonFlash = $(this, R.id.toggleButtonFlash);
        toggleButtonFlash.setText("打开手电筒");
        toggleButtonFlash.setTextOn("关闭手电筒");
        toggleButtonFlash.setTextOff("打开手电筒");
        toggleButtonFlash.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.toggleButtonFlash:
                if (isChecked) {
                    camera = Camera.open();
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview(); // 开始亮灯
                } else {
                    camera.stopPreview(); // 关掉亮灯
                    camera.release(); // 关掉照相机
                }


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) camera.release();
    }
}
