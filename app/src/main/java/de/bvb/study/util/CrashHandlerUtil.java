package de.bvb.study.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存运行时异常的信息到手机上<p/>
 * 文件路径:
 * /sdcard/Android/data/%package_name%/files/logs/crash_yyyy_MM_dd.log<br/>
 * TODO 异常会保存3次, 应用会重启两次,这个不知道什么原因
 */
public class CrashHandlerUtil  implements Thread.UncaughtExceptionHandler {

    private final String FILE_NAME_FORMAT = "yyyy_MM_dd";
    private final String FILE_NAME_PREFIXES = "crash_";
    private final String FILE_NAME_EXTENSION = ".log";
    private final String CRASH_TIME_FORMAT = "[yyyy-MM-dd HH:mm:ss:sss]";
    private final String FILE_PATH = "logs";

    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    /** 文件路径 */
    private String filePath;
    private String fileName;

    ///////////////////////////////////////////////////////////////////////////
    // 单例模式
    private static CrashHandlerUtil mInstance = new CrashHandlerUtil();

    private CrashHandlerUtil() {}

    public static CrashHandlerUtil getInstance() {
        return mInstance;
    }
    ///////////////////////////////////////////////////////////////////////////

    /** 在 自定义的Application 中 调用此方法即可 */
    public void init(Context context) {
        mContext = context;
        filePath = mContext.getExternalFilesDir(FILE_PATH) + File.separator;
        fileName = FILE_NAME_PREFIXES + new SimpleDateFormat(FILE_NAME_FORMAT).format(System.currentTimeMillis()) + FILE_NAME_EXTENSION;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /** 异常发生时，系统回调的函数，我们在这里处理一些操作 */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 如果没有自定义处理方式就使用系统的方式
        if (!handleException(ex) && defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            // 让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            SystemClock.sleep(1000);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /** 自定义 crash 处理 */
    private boolean handleException(Throwable ex) {
        if (ex == null) return true;
        // 保存信息
        saveInfo(mContext, ex);

        // 显示提示信息，需要在线程中显示Toast
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare(); //在子线程中显示Toast 需要显示调用  Looper.prepare() 和  Looper.loop() 这2个方法
                Toast.makeText(mContext, "很抱歉，程序遭遇异常，即将退出！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

        return true;
    }

    /** 保存数据 */
    private String saveInfo(Context context, Throwable ex) {
        StringBuffer sb = new StringBuffer("\n");
        // crash 的时间
        sb.append("crashTime = ").append(new SimpleDateFormat(CRASH_TIME_FORMAT).format(System.currentTimeMillis())).append("\n");
        // 设备信息
        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        // 异常信息
        sb.append(obtainExceptionInfo(ex));
        // 数据存储
        File dir = new File(filePath);
        if (!dir.exists()) dir.mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(filePath + fileName, true); // 追加的方式
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }


    /** 获取设备信息 */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);

        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);

        return map;
    }


    /** 获取 crash 信息 */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        return mStringWriter.toString();
    }

}