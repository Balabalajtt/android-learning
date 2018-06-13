package com.utte.aidltest.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.utte.aidltest.Constants;
import com.utte.aidltest.R;

import static com.utte.aidltest.Constants.MSG_FROM_SERVER;

public class MessengerActivity extends AppCompatActivity {
    private Messenger mMessenger;
    private Message mMessage;
    private static final String TAG = "MessengerActivity";
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接时使用返回的IBinder创建Messenger
            mMessenger = new Messenger(service);
            //创建装消息的Message
            mMessage = Message.obtain(null, Constants.MSG_FROM_CLIENT);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    private Messenger mReplyMessenger = new Messenger(new MessengerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this, MessengerService.class);
        //绑定Service
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //Activity销毁时解除绑定
        unbindService(mConnection);
        super.onDestroy();
    }

    public void onClick(View view) {
        Bundle data = new Bundle();
        data.putString("msg", "hello from client.");
        //将需要传递的数据装入Message
        mMessage.setData(data);
        //将回复的Messenger装进Message
        mMessage.replyTo = mReplyMessenger;
        try {
            Log.d(TAG, "onClick: " + "send");
            //通过Messenger发送Message
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_SERVER:
                    Log.d(TAG, "handleMessage: " + msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
