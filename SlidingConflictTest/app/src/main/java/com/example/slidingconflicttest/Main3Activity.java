package com.example.slidingconflicttest;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements StickyLayout.OnGiveUpTouchEventListener {

    private StickyLayout mStickyLayout;
    private ImageView mImage;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mStickyLayout = findViewById(R.id.container);
        mStickyLayout.setOnGiveUpTouchEventListener(this);
//        mStickyLayout.setSticky(true);
        mImage = findViewById(R.id.header);
        mListView = findViewById(R.id.list);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("num " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_list_item, R.id.name, data);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        View view = mListView.getChildAt(0);
        if (view != null && view.getTop() >= 0) {
            return true;
        }
        return false;
    }
}
