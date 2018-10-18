package com.utte.tinkertest.tinker.module;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * CustomPatchListener用来拓展DefaultPatchListener
 * DefaultPatchListener有两个可以重写的方法，onPatchReceive()和checkPatch()。
 * 当接收到一个Patch文件时，onPatchReceive()会被调用，
 * 在其中会先调用checkPatch()来检查patch文件合法性，通过了就会启动Service安装patch文件。
 * DefaultPatchListener的作用：
 * 1. 校验patch文件是否合法
 * 2. 校验合法后启动Service去安装patch文件
 */
public class CustomPatchListener extends DefaultPatchListener {
    private String currentMD5;

    public void setCurrentMD5(String currentMD5) {
        this.currentMD5 = currentMD5;
    }


    public CustomPatchListener(Context context) {
        super(context);
    }

    // 扩展patchCheck()
    @Override
    protected int patchCheck(String path, String patchMd5) {
        // 扩展的md5判断
//        if (!isFileMD5Matched(path, currentMD5)) {
//            return ShareConstants.ERROR_PATCH_DISABLE;
//        }
        return super.patchCheck(path, patchMd5);
    }

    private static boolean isFileMD5Matched(String path, String md5) {
        MessageDigest digest = null;
        try {
            InputStream is = new FileInputStream(new File(path));
            byte[] buffer = new byte[1024];
            int numRead;
            digest = MessageDigest.getInstance("MD5");
            while((numRead = is.read(buffer)) > 0) {
                digest.update(buffer, 0, numRead);
            }
            is.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest != null &&
                md5.equals(bufferToHex(digest.digest(), digest.digest().length));
    }

    private static String bufferToHex(byte[] ret,int length) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & ret[i]));
            while (h.length() < 2) {
                h.insert(0, "0");
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

}
