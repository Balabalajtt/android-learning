package com.utte.listviewandrecyclerview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestUtil {

    private static final String TAG = "RequestUtil";

    public static Bitmap downloadBitmap(String imageUrl) {
        Bitmap bitmap;
        URL url = null;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        bitmap = ReduceBitmap.reduceDecodeStream(url, 20, 40);

        return bitmap;
    }

}