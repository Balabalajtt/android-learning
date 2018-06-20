package com.utte.aidltest.pool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BinderPoolService extends Service {
    private Binder mBinder = new BinderPool.BinderPoolImpl();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
