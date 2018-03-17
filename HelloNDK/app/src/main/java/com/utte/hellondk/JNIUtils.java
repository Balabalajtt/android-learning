package com.utte.hellondk;

/**
 * Created by 江婷婷 on 2018/3/17.
 */

public class JNIUtils {
    public native String stringFromJNI();

    static {
        System.loadLibrary("native-lib");
    }
}
