package de.bvb.study.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RunTimeUtil extends BaseUtil {

    /** 通过查询su文件的方式判断手机是否root */
    public static boolean hasRootedSilent() {
        return new File("/system/bin/su").exists()
                || new File("/system/xbin/su").exists()
                || new File("/system/sbin/su").exists()
                || new File("/sbin/su").exists()
                || new File("/vendor/bin/su").exists();
    }

    /** 通过执行命令的方式判断手机是否root, 会有申请root权限的对话框出现 */
    public static boolean hasRooted() {
        return execSilent("echo test has rooted");
    }

    /** 执行命令获取结果集 */
    public static List<String> exec(String cmd) {
        List<String> dataList = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            runCmd(writer, cmd);
            process.waitFor();
            dataList = new ArrayList<>();
            String content;
            while (null != (content = reader.readLine())) {
                dataList.add(content);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.closeCloseable(reader, writer);
            if (process != null) process.destroy();
        }
        return dataList;
    }

    /** 执行一组命令 */
    public static void execSilent(String... cmd) {
        BufferedWriter writer = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            runCmd(writer, cmd);
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.closeCloseable(writer);
            if (process != null) process.destroy();
        }
    }

    /** 判断进程是否正在运行 */
    public static boolean isProcessRunning(String processName) {
        List<String> processList = exec("ps");
        for (int i = 1; i < processList.size(); i++) {
            if (processList.get(i).endsWith(processName)) {
                return true;
            }
        }
        return false;
    }

    /** 判断是否成功执行 */
    public static boolean execSilent(String cmd) {
        boolean result = false;
        BufferedWriter writer = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            runCmd(writer, cmd);
            process.waitFor();
            result = process.exitValue() == 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtil.closeCloseable(writer);
            if (process != null) process.destroy();
        }
        return result;
    }

    // 执行命令
    private static void runCmd(BufferedWriter writer, String... cmd) throws IOException {
        for (int i = 0; i < cmd.length; i++) {
            writer.write(cmd[i] + "\n");
            writer.flush();
            LogUtil.d("runCmd:   " + cmd[i]);
        }
        writer.write("exit \n");
        writer.flush();
    }
}
