package de.bvb.study.util;

/**
 * 打开或者关闭adb 无线调试
 * 需要手机root
 */
public class AdbWireUtil {
    public static void openAdbWireless() {
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (RunTimeUtil.hasRooted()) {
                    String[] cmd = {"su", "setprop service.adb.tcp.port 5555", "stop adbd", "start adbd"};
                    RunTimeUtil.execSilent(cmd);
                }
            }
        });
    }

    public static void closeAdbWireless() {
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (RunTimeUtil.hasRooted()) {
                    String[] cmd = {"su", "setprop service.adb.tcp.port -1", "stop adbd", "start adbd"};
                    RunTimeUtil.execSilent(cmd);
                }
            }
        });
    }

}
