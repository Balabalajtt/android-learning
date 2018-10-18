package com.utte.tinkertest.tinker.module;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 1. 从服务器下载patch文件
 * 2. 调用TinkerManager完成patch文件加载
 * 3. patch文件在下次进程启动时生效
 */
public class TinkerService extends Service {
    private static final String TAG = "TinkerService";
    private static final String FILE_END = ".apk";
    // handler判断
    private static final int DOWNLOAD_PATCH = 0x01;// 下载patch文件
    private static final int UPDATE_PATCH = 0x02;// 检查是否有patch更新

    private String mPatchFileDir;// patch要保存的文件夹
    private String mFilePatch;// 要加载的patch文件保存路径
    private BasePatch mBasePatchInfo;// 服务器patch信息

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PATCH:
                    checkPatchInfo();
                    break;
                case DOWNLOAD_PATCH:
                    downloadPatch();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    // 表示Service真正被启动了
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 检查是否有patch更新
        mHandler.sendEmptyMessage(UPDATE_PATCH);
        return START_NOT_STICKY;
    }

    // 初始化变量
    private void init() {
        // /storage/emulated/0/Android/data/com.utte.tinkertest/cache/tpatch/
        mPatchFileDir = getExternalCacheDir().getAbsolutePath() + "/tpatch/";
        File file = new File(mPatchFileDir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    // 检查path文件更新信息
    private void checkPatchInfo() {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://www.utte.com").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stopSelf();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String url = response.body().string();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    mBasePatchInfo = new BasePatch();
                    mBasePatchInfo.url = url;
                    Log.d(TAG, "onResponse: " + url);
                    mHandler.sendEmptyMessage(DOWNLOAD_PATCH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // 下载patch文件
    private void downloadPatch() {
        mFilePatch = mPatchFileDir
                .concat(String.valueOf(System.currentTimeMillis()))
                .concat(FILE_END);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(mBasePatchInfo.url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stopSelf();
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;//输入流
                FileOutputStream fos = null;//输出流
                try {
                    is = response.body().byteStream();//获取输入流
                    fos = new FileOutputStream(new File(mFilePatch));
                    byte[] buf = new byte[1024];
                    int ch;
                    while ((ch = is.read(buf)) != -1) {
                        fos.write(buf, 0, ch);
                    }
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 还要传入文件的md5值
                TinkerManager.loadPatch(mFilePatch, mBasePatchInfo.md5);
            }
        });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
