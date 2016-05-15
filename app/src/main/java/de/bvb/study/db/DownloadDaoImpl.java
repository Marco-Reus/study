package de.bvb.study.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.entity.download.ThreadInfo;

public class DownloadDaoImpl implements DownloadDao {

    private DownloadSQLiteOpenHelper helper;

    /////////////////////////////////////////////////////////////////////////////////
    //单例-开始///////////////////////////////////////////////////////////////////////
    private DownloadDaoImpl() {
        helper = DownloadSQLiteOpenHelper.getInstance();
    }

    private static class SingletonHolder {
        private static DownloadDaoImpl instance = new DownloadDaoImpl();
    }

    public static DownloadDaoImpl getInstance() {
        return SingletonHolder.instance;
    }
    //单例-结束///////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void insert(ThreadInfo threadInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into thread_info (threadId,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.threadId, threadInfo.url, threadInfo.start, threadInfo.end, threadInfo.finished});
        db.close();
    }

    @Override
    public void delete(String url, int threadId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from  thread_info where threadId=? and url=?",
                new Object[]{threadId, url});
        db.close();
    }

    @Override
    public void update(String url, int threadId, long finished) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update thread_info set finished =?  where threadId=? and url=?",
                new Object[]{finished, threadId, url});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreadInfoList(String url) {
        List<ThreadInfo> threadInfoList = null;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select threadId,url,start,end,finished from thread_info where url=?", new String[]{url});
        if (null != cursor) {
            threadInfoList = new ArrayList<>();
            while (cursor.moveToNext()) {
                ThreadInfo threadInfo = new ThreadInfo();
                threadInfo.threadId = cursor.getInt(cursor.getColumnIndex("threadId"));
                threadInfo.url = cursor.getString(cursor.getColumnIndex("url"));
                threadInfo.start = cursor.getInt(cursor.getColumnIndex("start"));
                threadInfo.end = cursor.getInt(cursor.getColumnIndex("end"));
                threadInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
                threadInfoList.add(threadInfo);
            }
            cursor.close();
        }
        db.close();
        return threadInfoList;
    }

    @Override
    public boolean isExists(String url, int threadId) {
        boolean isExists = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select 1 from thread_info where threadId=? and url=?", new String[]{String.valueOf(threadId), url});
        if (null != cursor) {
            isExists = cursor.moveToFirst();
            cursor.close();
        }
        db.close();
        return isExists;
    }
}
