package com.example.administrator.test.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.test.R;
import com.example.administrator.test.dao.NewsDao;
import com.example.administrator.test.domain.NewsBean;
import com.example.administrator.test.utils.NewsUtil;
import com.example.administrator.test.view.MyImageView;

import java.util.ArrayList;

/**
 * 新闻案例网络版
 * 2017年12月9日21:22:35
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lv;
    private ArrayList<NewsBean> mList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mNewsAdapter.notifyDataSetChanged();
        }
    };
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();
    }

    private void initData() {
        // 1.先去本地数据库获取所有新闻数据
        NewsDao newsDao = new NewsDao(this);
        mList = newsDao.query();
        mNewsAdapter = new NewsAdapter();
        lv.setAdapter(mNewsAdapter);
        // 2.再开启一个子线程,去请求网络获取所有新闻数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                String json_url = "http://10.0.2.2:8080/news.json";
                mList = NewsUtil.getAllNewsFromServer(json_url, getApplicationContext());
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initUI() {
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsBean newsBean = mList.get(position);
                String news_url = newsBean.news_url;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news_url));
                startActivity(intent);
            }
        });
    }

    private class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public NewsBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.listview_item, null);
                viewHolder.iv_icon = (MyImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
                viewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NewsBean newsBean = getItem(position);
            viewHolder.tv_title.setText(newsBean.title);
            viewHolder.tv_des.setText(newsBean.des);
            viewHolder.tv_comment.setText(newsBean.comment + "");
            switch (newsBean.type) {
                case 1:
                    viewHolder.tv_type.setText("头条");
                    break;
                case 2:
                    viewHolder.tv_type.setText("娱乐");
                    break;
                case 3:
                    viewHolder.tv_type.setText("科技");
                    break;
            }
            viewHolder.iv_icon.loadImageformUrl(newsBean.icon_url);
            return convertView;
        }
    }

    private class ViewHolder {
        private MyImageView iv_icon;
        private TextView tv_title;
        private TextView tv_des;
        private TextView tv_comment;
        private TextView tv_type;
    }


}
