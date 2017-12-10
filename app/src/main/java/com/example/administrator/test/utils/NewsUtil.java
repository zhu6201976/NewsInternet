package com.example.administrator.test.utils;

import android.content.Context;
import android.util.Log;

import com.example.administrator.test.dao.NewsDao;
import com.example.administrator.test.domain.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * NewsUtil
 * Created by My on 2017/12/9.
 */

public class NewsUtil {
    private static final String TAG = "NewsUtil";

    public static ArrayList<NewsBean> getAllNewsFromServer(String path, Context context) {
        ArrayList<NewsBean> list = new ArrayList<>();
        NewsBean newsBean;
        // 1.请求网络,获取所有新闻数据
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2 * 1000);
            conn.setReadTimeout(2 * 1000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                String json = StreamUtil.streamToString(inputStream);
                Log.d(TAG, "getAllNewsFromServer: json:" + json);
                // 解析json
                JSONObject jsonObject = new JSONObject(json);
                JSONArray newss = jsonObject.getJSONArray("newss");
                for (int i = 0; i < newss.length(); i++) {
                    newsBean = new NewsBean();
                    JSONObject news = newss.getJSONObject(i);
                    newsBean._id = news.getInt("id");
                    newsBean.title = news.getString("title");
                    newsBean.des = news.getString("des");
                    newsBean.time = news.getString("time");
                    newsBean.news_url = news.getString("news_url");
                    newsBean.icon_url = news.getString("icon_url");
                    newsBean.comment = news.getInt("comment");
                    newsBean.type = news.getInt("type");
                    list.add(newsBean);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        // 2.缓存本地一份(如果有),缓存之前,清空原有数据库内容
        NewsDao newsDao = new NewsDao(context);
        if (list.size() > 0) {
            newsDao.delete();
            newsDao.insert(list);
        }
        return list;
    }
}
