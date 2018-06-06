package com.utte.oopsixprinciple.loader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.utte.oopsixprinciple.loader.utils.CloseUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/**
 * Created by 江婷婷 on 2018/6/5.
 */

public class DiskCache implements ImageCache {
    private static String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    @Override
    public Bitmap get (String url) {
        return BitmapFactory.decodeFile(cacheDir + getMD5(url));
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + getMD5(url));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeQuietly(fileOutputStream);
        }
    }

    private String getMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
