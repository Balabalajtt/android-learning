package com.utte.aidltest.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.utte.aidltest.Constants.MSG_FROM_CLIENT;
import static com.utte.aidltest.Constants.MSG_FROM_SERVER;

/**
 * Created by 江婷婷 on 2018/6/11.
 */

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    //根据Handler对象创建Messenger对象
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    //定义一个Handler
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //接收到客户端消息时打印
                case MSG_FROM_CLIENT:
                    Log.d(TAG, "handleMessage: " + msg.getData().getString("msg"));
                    //从客户端发送的Message中拿到回复的Messenger
                    Messenger clientMessenger = msg.replyTo;
                    //创建回复的Message
                    Message replyMessage = Message.obtain(null, MSG_FROM_SERVER);
                    Bundle replyData = new Bundle();
                    replyData.putString("msg", "reply from server.");
                    //装进Message
                    replyMessage.setData(replyData);
                    try {
                        //发送回复
                        clientMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //onBind返回Messenger对象底层的Binder
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
