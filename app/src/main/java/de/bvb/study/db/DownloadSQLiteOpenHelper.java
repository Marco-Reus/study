package de.bvb.study.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

import de.bvb.study.MyApplication;

/**
 * Created by Administrator on 2016/5/14.
 */
public class DownloadSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final boolean isDebug = MyApplication.isDebug;
    public static final String FILE_NAME = "download";
    public static final String FILE_PATH = MyApplication.getContext().getExternalFilesDir("databases").getAbsolutePath() + File.separator;
    public static final int VERSION = 1;

    public static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement" +
            ",threadId integer,url text,start integer,end integer,finished integer)";
    public static final String SQL_DELETE = "drop table thread_info if exists";


    /////////////////////////////////////////////////////////////////////////////////
    //单例-开始///////////////////////////////////////////////////////////////////////
    private DownloadSQLiteOpenHelper() {
        super(MyApplication.getContext(), isDebug ? (FILE_PATH + FILE_NAME) : FILE_NAME, null, VERSION);
    }

    private static class SingletonHolder {
        private static DownloadSQLiteOpenHelper instance = new DownloadSQLiteOpenHelper();

    }

    public static DownloadSQLiteOpenHelper getInstance() {
        return SingletonHolder.instance;
    }
    //单例-结束///////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        db.execSQL(SQL_CREATE);
    }
}
