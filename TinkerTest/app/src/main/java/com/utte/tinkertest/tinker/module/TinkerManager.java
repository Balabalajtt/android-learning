package com.utte.tinkertest.tinker.module;

import android.content.Context;
import android.util.Log;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

/**
 * 对Tinker的api做一层封装
 * 两个api：
 * TinkerInstaller.install()
 * TinkerInstaller.onReceiveUpgradePatch()
 */
public class TinkerManager {

    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;

    /**
     * 完成Tinker的初始化
     * @param appLike
     */
    public static void installTinker(ApplicationLike appLike) {
        mAppLike = appLike;
        if (isInstalled) {
            return;
        }

//        TinkerInstaller.install(mAppLike);// 简单的初始化方法

        LoadReporter loadReporter = new DefaultLoadReporter(getApplicationContext());
        PatchReporter patchReporter = new DefaultPatchReporter(getApplicationContext());
        DefaultPatchListener patchListener = new CustomPatchListener(getApplicationContext());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();
        TinkerInstaller.install(mAppLike,
                loadReporter,// 日志上报和监听安装结果，重点监听patch文件在加载过程中出现的事件
                patchReporter,// 日志上报和监听安装结果，重点监听patch文件合成阶段出现的事件
                patchListener,
                CustomResultService.class,// 决定在patch文件安装完毕后的操作
                upgradePatchProcessor// 决定patch文件的安装策略
        );// 复杂的初始化方法

        // 官方simple
//        LoadReporter loadReporter = new SampleLoadReporter(appLike.getApplication());
//        //or you can just use DefaultPatchReporter
//        PatchReporter patchReporter = new SamplePatchReporter(appLike.getApplication());
//        //or you can just use DefaultPatchListener
//        PatchListener patchListener = new SamplePatchListener(appLike.getApplication());
//        //you can set your own upgrade patch if you need
//        AbstractPatch upgradePatchProcessor = new UpgradePatch();
//
//        TinkerInstaller.install(appLike,
//                loadReporter, patchReporter, patchListener,
//                DefaultTinkerResultService.class, /*SampleResultService.class, */upgradePatchProcessor);

        isInstalled = true;
    }

    private static final String TAG = "TinkerManager";

    /**
     * 完成Patch文件的加载
     * @param path
     */
    public static void loadPatch(String path, String md5) {
        if (Tinker.isTinkerInstalled()) {
            // 把md5值设置给PatchListener
//            mPatchListener.setCurrentMD5(md5);
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike对象获取ApplicationContext
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication()/*.getApplicationContext()*/;
        }
        return null;
    }

}
