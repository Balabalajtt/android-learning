package com.utte.hooktest;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookStartActivity {
    /**
     * 把要启动的Activity替换成注册过的StubActivity
     */
    public static void hookActivityManager() {
        // 1. 拿到ActivityManager#IActivityManagerSingleton静态对象
        Object singleton = ReflectUtils.getStaticFieldObject("android.app.ActivityManager", "IActivityManagerSingleton");
        // 2. 拿到IActivityManagerSingleton对象的mInstance字段
        Object instance = ReflectUtils.getFieldObject("android.util.Singleton", singleton, "mInstance");
        try {
            // 4. 代理对象需要实现的接口
            Class<?> inf = Class.forName("android.app.IActivityManager");
            // 5. 创建代理对象
            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{inf},
                    new HookActivityHandler(instance)
            );
            // 6. 用代理去替代IActivityManagerSingleton的mInstance字段
            ReflectUtils.setFieldObject("android.util.Singleton", singleton, "mInstance", proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 换回真正启动的Activity
     */
    public static void hookActivityThread() {
        // 1. 获取当前ActivityThread对象
        Object currentActivityThread = ReflectUtils.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        // 2. 获取ActivityThread对象的mH
        Handler mH = (Handler) ReflectUtils.getFieldObject(currentActivityThread, "mH");
        // 3. 设置mCallback字段，拦截掉handleMessage
        ReflectUtils.setFieldObject(Handler.class, mH, "mCallback", new HandlerCallback(mH));
    }

}

class HookActivityHandler implements InvocationHandler {
    // 原始对象
    private Object origin;
    private static final String TAG = "HookInvocation";

    public HookActivityHandler(Object origin) {
        this.origin = origin;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 只变这一个方法的逻辑
        if ("startActivity".equals(method.getName())) {
            // 找到第一个Intent参数
            Intent originIntent;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            originIntent = (Intent) args[index];
            Intent newIntent = new Intent();
            // stub的包名，就是app的包名
            String stubPackage = originIntent.getComponent().getPackageName();
            // 替换成启动stub
            ComponentName componentName = new ComponentName(stubPackage, StubActivity.class.getName());
            newIntent.setComponent(componentName);
            // 先存zhe
            newIntent.putExtra("REALINTENT", originIntent);
            // 替换成StubActivity的intent
            args[index] = newIntent;
            Log.d(TAG, "invoke: " + "hook success !!!!!!!!!");
        }
        return method.invoke(origin, args);
    }

}

class HandlerCallback implements Handler.Callback {
    Handler mHandler;

    public HandlerCallback(Handler handler) {
        mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            // mH的100是启动Activity的逻辑
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        mHandler.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        // obj是ActivityClientRecord类型的
        Object obj = msg.obj;

        // obj有个字段存的intent就叫intent
        Intent intent = (Intent) ReflectUtils.getFieldObject(obj, "intent");
        // 取出真正Activity的intent
        Intent realIntent = intent.getParcelableExtra("REALINTENT");
        // 替换Component
        intent.setComponent(realIntent.getComponent());
    }


}