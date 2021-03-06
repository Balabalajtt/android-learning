package com.utte.oopsixprinciple;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.utte.oopsixprinciple.loader.ImageLoader;
import com.utte.oopsixprinciple.loader.cache.DoubleCache;
import com.utte.oopsixprinciple.loader.cache.ImageCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.im);
        final String url = "https://upload-images.jianshu.io/upload_images/5004304-e702523821c4bc34.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/700";

        final ImageLoader imageLoader = new ImageLoader();
        imageLoader.setImageCache(new ImageCache() {
            @Override
            public Bitmap get(String url) {
                //具体实现
                return null;
            }

            @Override
            public void put(String url, Bitmap bitmap) {
                //具体实现
            }
        });
        imageLoader.setImageCache(new DoubleCache());//使用双缓存

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLoader.displayImage(url, imageView);
            }
        });

    }
}
