package com.example.administrator.test.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.test.domain.NewsBean;

import java.util.ArrayList;

/**
 * NewsDao
 * Created by My on 2017/12/9.
 */

public class NewsDao {

    private MySqliteOpenHelper mSqliteOpenHelper;

    public NewsDao(Context context) {
        mSqliteOpenHelper = new MySqliteOpenHelper(context);
    }

    /**
     * 遍历插入所有新闻数据
     *
     * @param list 传递过来的新闻数据集合
     */
    public void insert(ArrayList<NewsBean> list) {
        SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
        for (NewsBean newsBean : list) {
            ContentValues values = new ContentValues();
            values.put("_id", newsBean._id);
            values.put("title", newsBean.title);
            values.put("des", newsBean.des);
            values.put("time", newsBean.time);
            values.put("news_url", newsBean.news_url);
            values.put("icon_url", newsBean.icon_url);
            values.put("comment", newsBean.comment);
            values.put("type", newsBean.type);
            db.insert("news", null, values);
        }
        db.close();
    }

    /**
     * 清空数据库所有新闻数据
     */
    public void delete() {
        SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
        db.delete("news", null, null);
        db.close();
    }

    public ArrayList<NewsBean> query() {
        ArrayList<NewsBean> list = new ArrayList<>();
        NewsBean newsBean;
        SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("news", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                newsBean = new NewsBean();
                newsBean._id = cursor.getInt(0);
                newsBean.title = cursor.getString(1);
                newsBean.des = cursor.getString(2);
                newsBean.time = cursor.getString(3);
                newsBean.news_url = cursor.getString(4);
                newsBean.icon_url = cursor.getString(5);
                newsBean.comment = cursor.getInt(6);
                newsBean.type = cursor.getInt(7);
                list.add(newsBean);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

}
