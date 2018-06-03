package com.utte.oopsixprinciple.srp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
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
    //图片缓存
    LruCache<String, Bitmap> mImageCache;
    //加载图片的线程池
    //newFixedThreadPool创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //ui线程的handler 用来将图片加载进ImageView
    Handler mUiHandler = new Handler(Looper.getMainLooper());

    public ImageLoader() {
        initImageCache();
    }

    /**
     * 初始缓存
     */
    private void initImageCache() {
        //获取当前可用内存大小
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //四分之一用来缓存
        final int cacheSize = maxMemory / 4;
        //设置LruCache缓存大小，重写sizeOf方法 单位要一致
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    /**
     * 全过程
     * @param url 图片地址
     * @param imageView view
     */
    public void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);
        //添加线程
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = mImageCache.get(url);
                if (bitmap == null) {
                    bitmap = downloadImage(url);
                    if (bitmap == null) {
                        return;
                    }
                    //放入缓存
                    mImageCache.put(url, bitmap);
                }
                //判断防止过程中又对imageView做了下载其他url图片的请求
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView, bitmap);
                }
            }
        });
    }

    /**
     * 图加载到view
     * @param imageView view
     * @param bitmap 图
     */
    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    /**
     * 下载图片
     * @param imageUrl 图片地址
     * @return bitmap
     */
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
