package com.utte.wifip2ptest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utte.wifip2ptest.socket.ReceiveSocket;

/**
 * 服务端监听的Service
 */
public class WifiP2pService extends IntentService {

    private static final String TAG = "WifiP2pService";
    private ReceiveSocket mReceiveSocket;

    public WifiP2pService() {
        super("WifiP2pService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public MyBinder() {
            super();
        }
        public void initListener(ReceiveSocket.ProgressReceiveListener listener){
            mReceiveSocket.setOnProgressReceiveListener(listener);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "服务启动了");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mReceiveSocket = new ReceiveSocket();
        mReceiveSocket.createServerSocket();
        Log.e(TAG, "传输完毕");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReceiveSocket.clean();
    }
}
