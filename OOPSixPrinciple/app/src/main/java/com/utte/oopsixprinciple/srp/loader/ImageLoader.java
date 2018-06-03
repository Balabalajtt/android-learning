package com.utte.oopsixprinciple.srp.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 江婷婷 on 2018/6/3.
 */

public class ImageLoader {
    ImageCache mImageCache = new ImageCache();
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Handler mUiHandler = new Handler(Looper.getMainLooper());

    public void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = mImageCache.get(url);
                if (bitmap == null) {
                    bitmap = downloadImage(url);
                    if (bitmap == null) {
                        return;
                    }
                    mImageCache.put(url, bitmap);
                }
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView, bitmap);
                }
            }
        });
    }

    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
