package cn.edu.twt.saishi_android.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by clifton on 16-3-2.
 */
public class CacheDbHelper extends SQLiteOpenHelper {
    public CacheDbHelper(Context context, int version) {
        super(context, "cache.db", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists CacheList (id INTEGER primary key autoincrement,date INTEGER unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}