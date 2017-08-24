package com.example.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_notice:
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager manager = (NotificationManager) getSystemService
                        (NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
//                        .setAutoCancel(true)
//                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
//                        .setVibrate(new long[] {0, 100, 100, 100})
//                        .setLights(Color.RED, 1000, 1000)
                        .setDefaults(android.support.v4.app.NotificationCompat.DEFAULT_ALL)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                                (BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                        .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX)
                        .build();
                manager.notify(1, notification);
                break;
        }
    }
}
