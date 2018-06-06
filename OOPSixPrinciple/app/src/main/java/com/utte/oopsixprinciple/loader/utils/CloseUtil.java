package com.utte.oopsixprinciple.loader.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by 江婷婷 on 2018/6/5.
 */

public class CloseUtil {
    private CloseUtil() {}
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
