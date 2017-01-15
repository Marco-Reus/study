package de.bvb.study.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import de.bvb.study.MyApplication;
import de.bvb.study.entity.download.FileInfo;
import de.bvb.study.util.LogUtil;
import de.bvb.study.util.StreamUtil;
import de.bvb.study.util.StringUtil;

public class MyDownloadService extends Service {
    public static final String ACTION_START = "start";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_UPDATE = "update";
    public static final String EXTRA_KEY_DOWNLOADING_PROGRESS = "EXTRA_KEY_DOWNLOADING_PROGRESS";
    public static final String EXTRA_KEY_FILE_INFO = "EXTRA_KEY_FILE_INFO";
    public static final String FILE_PATH = MyApplication.getContext().getExternalFilesDir("downloads") + File.separator;
    public static final int handle_what_refresh_file_length = 0;

    private MyDownloadTask downloadTask;
    private static final String TAG = "download";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case handle_what_refresh_file_length:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    LogUtil.d(TAG, fileInfo.toString());
                    // 拿到文件长度后,开始下载
                    downloadTask = new MyDownloadTask(MyDownloadService.this, fileInfo);
                    downloadTask.download();
                    break;
            }
        }
    };

    public static void startDownload(Activity activity, FileInfo fileInfo) {
        Intent intent = new Intent(activity, MyDownloadService.class);
        intent.putExtra(MyDownloadService.EXTRA_KEY_FILE_INFO, fileInfo);
        intent.setAction(MyDownloadService.ACTION_START);
        activity.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (ACTION_START.equals(intent.getAction())) {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(EXTRA_KEY_FILE_INFO);
                LogUtil.d(TAG, "init ..." + fileInfo.toString());

                new InitThread(fileInfo).start();
            } else if (ACTION_PAUSE.equals(intent.getAction())) {
                LogUtil.d(TAG, "pause");
                if (downloadTask != null) downloadTask.isPause = true;
            } else if (ACTION_UPDATE.equals(intent.getAction())) {
                LogUtil.d(TAG, "update");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 初始化类:<p/>
     * 1. 获取文件长度<br/>
     * 2. 更新实例 <br/>
     * 3. 创建文件<br/>
     */
    class InitThread extends Thread {
        FileInfo fileInfo;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            try {
                // 获取文件长度
                long length = 0l;
                connection = (HttpURLConnection) new URL(fileInfo.url).openConnection();
                connection.connect();
                connection.setConnectTimeout(3000);
                if (connection.getResponseCode() == 200) {
                    length = connection.getContentLength();
                    String contentName = connection.getHeaderField("Content-Disposition");
                    if (!TextUtils.isEmpty(contentName)) {
                        fileInfo.fileName = StringUtil.unicode2Cn(contentName.split("filename=")[1].replace("\"", ""));
                    }
                    LogUtil.d("download", connection.getHeaderField("Content-Type"));
                }
                if (length <= 0) return;
                // 回传,更新实例
                fileInfo.fileLength = length;
                Message.obtain(handler, handle_what_refresh_file_length, fileInfo).sendToTarget();
                //创建文件
                File dir = new File(FILE_PATH);
                if (!dir.exists()) dir.mkdir();
                LogUtil.d(TAG, "download path " + dir.getAbsolutePath());
                randomAccessFile = new RandomAccessFile(new File(dir, fileInfo.fileName), "rwd");
                randomAccessFile.setLength(length);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
                StreamUtil.closeCloseable(randomAccessFile);
            }
        }
    }
}
