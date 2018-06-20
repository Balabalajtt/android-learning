package com.utte.aidltest.pool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = "BinderPool";
    public static final int BINDER_COMBINE = 1;
    public static final int BINDER_COMPUTE = 2;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();//获取context，用于连接Service的
        connectBinderPoolService();//连接Service
    }

    //提供单例的BinderPool
    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    //连接远程Service
    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            //等待，跳至onServiceConnected()，实现同步获取连接池的Binder对象
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //从Service拿到Binder连接池的Binder对象
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                //处理断链
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
                } catch (RemoteException e) {
                e.printStackTrace();
            }
            //同步结束，跳回connectBinderPoolService()
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    //给客户端的queryBinder方法
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                //调用连接池Binder对象的queryBinder()
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    //IBinderPool AIDL接口实现
    public static class BinderPoolImpl extends IBinderPool.Stub {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            //根据不同的code返回不同的具体业务Binder实现类
            switch (binderCode) {
                case BINDER_COMBINE:
                    binder = new CombineImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
            }
            return binder;
        }
    }
}
