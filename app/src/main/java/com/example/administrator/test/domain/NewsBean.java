package com.example.administrator.test.domain;

/**
 * Created by My on 2017/12/9.
 */

public class NewsBean {

    public int _id;
    public int comment;
    public int type;
    public String title;
    public String des;
    public String time;
    public String news_url;
    public String icon_url;

    @Override
    public String toString() {
        return "NewsBean{" +
                "_id=" + _id +
                ", comment=" + comment +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", time='" + time + '\'' +
                ", news_url='" + news_url + '\'' +
                ", icon_url='" + icon_url + '\'' +
                '}';
    }
}
