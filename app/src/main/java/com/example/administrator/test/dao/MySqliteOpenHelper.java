package com.example.administrator.test.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by My on 2017/12/9.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public MySqliteOpenHelper(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 获取的新闻数据,包括id,皆已提供,缓存数据库id无需自增长
        db.execSQL("create table news(_id integer,title varchar(100),des varchar(100)," +
                "time varchar(100),news_url varchar(100),icon_url varchar(100),comment integer,type integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
