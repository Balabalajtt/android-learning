package com.example.drawabletest.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.drawabletest.R;
import com.example.drawabletest.photo.adapter.RecyclerViewAdapter;
import com.example.drawabletest.photo.view.ShowImagesDialog;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    StaggeredGridLayoutManager mLayoutManager;

    List<String> mUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        requestUrls();
        getDeviceDensity();

        mRecyclerView = findViewById(R.id.recycle_view);
        mLayoutManager = new StaggeredGridLayoutManager(4, 
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewAdapter = new RecyclerViewAdapter(mUrls, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (!mUrls.isEmpty()) {
                    new ShowImagesDialog(Main2Activity.this, mUrls, position).show();
                }
            }
        });

        Log.d(TAG, "onCreate: " + mUrls.size());

    }

    private void requestUrls() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String goal = "https://www.zhihu.com/question/54338311";
                mUrls.clear();
//                Log.d(TAG, "run: 11111111111111111111111111111111111");
                Connection conn;
                conn = Jsoup.connect(goal);
                conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
                conn.header("Host", "www.zhihu.com");
                conn.header("Referer", "https://www.zhihu.com/topic/19606965/hot");
                conn.header("Cookie", "q_c1=52982f8b821b44e79e6f761d93fc2357|1506525709000|1506525709000; q_c1=639c94471633436a84cea1deb07d8a72|1509804514000|1506525709000; _zap=ced7bf40-a461-42e0-8897-ed8d1a6eba12; l_cap_id=\"OTMyNDEzZjE0YTE1NGE3ZmJmNzU4MDU2NTg3NWMxNDA=|1510477212|d01d380b7a844b85b0aea4ec35360e9f0dc80a38\"; r_cap_id=\"MjgxNWNiZGU5MjNhNDRmOTk5NTQ2NjQ5MmNjNTQwY2Y=|1510477212|fdfa7963bbd980679571aad32ffe63229032c237\"; cap_id=\"NDgyY2NiYjY1NzFiNDcyYzg0YzgwMGRiNjE2MDE3NDI=|1510477212|5134359d564b0dcf4c487b65b6e83ac0bbfe5124\"; d_c0=\"ABBCwIlUfQyPTrpjg6Mo-7n_JB8To-1OlOU=|1507349199\"; __utma=51854390.77523427.1508236687.1510472527.1510477224.4; __utmz=51854390.1510477224.4.4.utmcsr=zhihu.com|utmccn=(referral)|utmcmd=referral|utmcct=/topic/19606965/newest; __utmv=51854390.000--|3=entry_date=20170927=1; aliyungf_tc=AQAAAF1sGxlktgoAVdggdWgSrQYfHYcq; _xsrf=8ebe5dc1cd13c8ba1f21014a12423926; __utmc=51854390; _xsrf=8ebe5dc1cd13c8ba1f21014a12423926; s-q=%E6%97%A5%E6%9C%AC; s-i=3; sid=iuuijf28; __utmb=51854390.0.10.1510477224");

                Document doc = null;
                try {
                    doc = conn.get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                System.out.println(doc);
                assert doc != null;
                Elements elements = doc.getElementsByClass("RichContent-inner");
                for (Element ele : elements) {
//                    Log.d(TAG, "run: 2222222222222");
                    Elements deu = ele.getElementsByTag("img");
                    for (Element e : deu) {
//                        Log.d(TAG, "run: 333333333333333");
                        String url= e.attr("src");
                        if (url.length() > 5 && url.substring(0, 4).equals("http")) {
                            mUrls.add(url);
                            Log.d(TAG, "run: " + url);
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

}
