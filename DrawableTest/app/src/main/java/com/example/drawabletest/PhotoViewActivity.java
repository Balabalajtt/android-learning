package com.example.drawabletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.drawabletest.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    private PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        photoView = findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.pica);
    }
}
