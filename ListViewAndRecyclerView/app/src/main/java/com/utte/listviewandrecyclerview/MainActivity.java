package com.utte.listviewandrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView mListView;
    private String[] data = { "Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
//        mListView = findViewById(R.id.lv);
//        mListView.setAdapter(adapter);
        mListView = findViewById(R.id.lv);
        ImageAdapter2 adapter = new ImageAdapter2(this, 0, ImagesData.imageUrls);
        mListView.setAdapter(adapter);

        Log.d(TAG, "onCreate: " + (Runtime.getRuntime().maxMemory() / 1024 / 1024));

    }

}