package com.utte.oopsixprinciple.loader.cache;

import android.graphics.Bitmap;

/**
 * Created by 江婷婷 on 2018/6/5.
 */

public interface ImageCache {
    Bitmap get(String url);
    void put(String url, Bitmap bitmap);
}
