package de.bvb.study.util;

import android.hardware.Camera;

/**
 * Created by Administrator on 2017/1/15.
 */
public class CameraUtil {

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式
    private CameraUtil() {}

    private static class Holder {
        public static final CameraUtil INSTANCE = new CameraUtil();
    }

    public static CameraUtil getInstance() {
        return Holder.INSTANCE;
    }
    ///////////////////////////////////////////////////////////////////////////

    private static Camera camera;
    private boolean isOpen = false;

    /** 打开闪光灯 */
    public void openFlash() {
        if (!isOpen) {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview(); // 开始亮灯
            isOpen = true;
        }
    }

    /** 关闭闪光灯 */
    public void closeFlash() {
        if (camera != null) {
            camera.stopPreview(); // 关掉亮灯
            camera.release(); // 关掉照相机
            isOpen = false;
        }

    }

    /** 释放资源 */
    public void release() {
        closeFlash();
        camera = null;
    }
}
