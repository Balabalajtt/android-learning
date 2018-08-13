package com.utte.remoteviewstest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private IntentFilter mIntentFilter;
    private SimpleAppWidget mWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mIntentFilter = new IntentFilter("com.utte.action.CLICK");
        mIntentFilter.addAction("android.appwidget.action.APPWIDGET_UPDATE");
        registerReceiver(mWidget, mIntentFilter);
    }

    public void onClick(View view) {

       sendCustomNotification();

    }

    private void sendNormalNotification() {
        String id = "utte_channel_01";
        String name="utte_channel";

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Notification.Builder builder;

        if (manager != null) {
            // 适配8.0
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(mChannel);
                builder = new Notification.Builder(mContext, id);
            } else {
                builder = new Notification.Builder(mContext);
            }
            // 构建通知
            notification = builder
                    .setTicker("h")
                    .setContentTitle("hello")
                    .setContentText("hello world")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true) // 自动消除
                    .setContentIntent(PendingIntent.getActivity(mContext, 0,
                            new Intent(this, MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT)) // 设置跳转
                    .build();
            // 发送通知
            manager.notify(0, notification);
        }
    }

    private void sendCustomNotification() {


        String id = "utte_channel_01";
        String name="utte_channel";

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Notification.Builder builder;

        if (manager != null) {
            // 适配8.0
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(mChannel);
                builder = new Notification.Builder(mContext, id);
            } else {
                builder = new Notification.Builder(mContext);
            }

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_custom);
            remoteViews.setTextViewText(R.id.tv, "hello");
            remoteViews.setImageViewResource(R.id.im, R.drawable.aa);
            PendingIntent imClickPendingIntent = PendingIntent.getActivity(mContext, 0,
                    new Intent(this, MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.im, imClickPendingIntent);

            // 构建通知
            notification = builder
                    .setContent(remoteViews)
                    .setTicker("h")
                    .setContentTitle("hello")
                    .setContentText("hello world")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true) // 自动消除
//                    .setContentIntent(PendingIntent.getActivity(mContext, 0,
//                            new Intent(this, MainActivity.class),
//                            PendingIntent.FLAG_UPDATE_CURRENT)) // 设置跳转
                    .build();
            // 发送通知
            manager.notify(0, notification);
        }
    }
}
