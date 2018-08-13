package com.utte.remoteviewstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class BActivity extends AppCompatActivity {

    private static final String TAG = "BActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void onClick(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.tv, "from BActivity");
        Intent intent = new Intent();
        intent.setAction("com.utte.MYBroadcast");
        Log.d(TAG, "onClick: ");
        intent.putExtra("remoteViews", remoteViews);
        sendBroadcast(intent);
    }
}
