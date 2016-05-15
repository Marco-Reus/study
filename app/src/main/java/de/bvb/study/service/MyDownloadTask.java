package de.bvb.study.service;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import de.bvb.study.db.DownloadDao;
import de.bvb.study.db.DownloadDaoImpl;
import de.bvb.study.entity.download.FileInfo;
import de.bvb.study.entity.download.ThreadInfo;
import de.bvb.study.util.ListUtil;
import de.bvb.study.util.LogUtil;
import de.bvb.study.util.StreamUtil;

/**
 * 下载任务
 */
public class MyDownloadTask {

    public boolean isPause;

    private static final String TAG = "download";
    private int mFinished;
    private Context context;
    private FileInfo fileInfo;
    private DownloadDao dao;

    public MyDownloadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        dao = DownloadDaoImpl.getInstance();
    }

    public void download() {
        List<ThreadInfo> threadInfoList = dao.getThreadInfoList(fileInfo.url);
        ThreadInfo threadInfo = null;
        if (ListUtil.isEmpty(threadInfoList)) {
            threadInfo = new ThreadInfo(0, fileInfo.url, 0, fileInfo.fileLength, 0);
        } else {
            threadInfo = threadInfoList.get(0);
        }
        new DownloadThread(threadInfo).start();
    }

    /**
     * 下载线程:<p/>
     * 1. 向数据库插入线程信息<br/>
     * 2. 设置下载位置<br/>
     * 3. 设置文件写入位置<br/>
     * 4. 开始下载<br/>
     */
    class DownloadThread extends Thread {
        private ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        public void run() {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            RandomAccessFile randomAccessFile = null;
            try {
                // 1. 向数据库插入线程信息
                if (!dao.isExists(threadInfo.url, threadInfo.threadId)) dao.insert(threadInfo);
                // 2. 设置下载位置
                connection = (HttpURLConnection) new URL(threadInfo.url).openConnection();
                connection.setConnectTimeout(3000);
                long start = threadInfo.start + threadInfo.finished;
                LogUtil.d(TAG, String.format("Range bytes=%d-%d", start, threadInfo.end));
                connection.setRequestProperty("Range", String.format("bytes=%d-%d", start, threadInfo.end));
                // 3. 设置文件写入位置
                randomAccessFile = new RandomAccessFile(new File(MyDownloadService.FILE_PATH, fileInfo.fileName), "rwd");
                randomAccessFile.seek(start);

                Intent intent = new Intent(MyDownloadService.ACTION_UPDATE); // intent 用来发送广播
                mFinished += threadInfo.finished;
                long time = System.currentTimeMillis(); // 用来每500ms 发送广播

                // 4. 开始下载
                if (connection.getResponseCode() != 206) return; //下载部分文件是206,不是200
                // 读取数据,写入文件
                inputStream = connection.getInputStream();
                byte[] buf = new byte[1024 * 8];
                int len;
                while (-1 != (len = inputStream.read(buf))) {
                    randomAccessFile.write(buf, 0, len);
                    // 广播发送下载的进度
                    mFinished += len;
                    if (System.currentTimeMillis() - time > 500) {
                        double progress = Double.parseDouble(new DecimalFormat("#.00").format(mFinished * 100d / fileInfo.fileLength));
                        intent.putExtra(MyDownloadService.EXTRA_KEY_DOWNLOADING_PROGRESS, progress);
                        LogUtil.d(TAG, "send progress " + progress);
                        context.sendBroadcast(intent);
                        time = System.currentTimeMillis();
                    }
                    // 暂停时保存进度
                    if (isPause) {
                        dao.update(threadInfo.url, threadInfo.threadId, mFinished);
                        return;
                    }
                }
                // 下载完成后删除数据库信息
                dao.delete(threadInfo.url, threadInfo.threadId);
                // 处理计算误差 如 99.99% 这种进度,但是实际已经下载完了
                intent.putExtra(MyDownloadService.EXTRA_KEY_DOWNLOADING_PROGRESS, 100d);
                context.sendBroadcast(intent);
                LogUtil.d(TAG, "send progress " + 100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
                StreamUtil.closeCloseable(randomAccessFile, inputStream);
            }
        }
    }
}
