package com.utte.oopsixprinciple.loader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by 江婷婷 on 2018/6/5.
 */

public class MemoryCache implements ImageCache {

    LruCache<String, Bitmap> mImageCache;

    public MemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return mImageCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

}
