package com.example.slidingconflicttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 横竖滑动冲突内部拦截解决
 */

public class Main2Activity extends AppCompatActivity {

    private HorizontalScrollViewTwo mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = findViewById(R.id.container);
        final int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
        for(int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView title = layout.findViewById(R.id.title);
            title.setText("第" + i + "页");
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListViewTwo listView = layout.findViewById(R.id.list);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("num " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_list_item, R.id.name, data);
        listView.setAdapter(adapter);
    }
}
