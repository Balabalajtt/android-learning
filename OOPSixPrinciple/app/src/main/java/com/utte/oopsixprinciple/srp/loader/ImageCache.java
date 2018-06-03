package com.utte.oopsixprinciple.srp.loader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by 江婷婷 on 2018/6/3.
 */

public class ImageCache {

    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        initImageCache();
    }

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

    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        return mImageCache.get(url);
    }

}
