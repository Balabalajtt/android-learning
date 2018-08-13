package com.utte.remoteviewstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class AActivity extends AppCompatActivity {

    private LinearLayout mContent;

    private static final String TAG = "AActivity";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            RemoteViews remoteViews = intent.getParcelableExtra("remoteViews");
            if (remoteViews != null) {
                Log.d(TAG, "onReceive: ");
//                View view = remoteViews.apply(context, mContent);
//                mContent.addView(view);
                int layoutId = getResources().getIdentifier("widget", "layout", getPackageName());
                View view = getLayoutInflater().inflate(layoutId, mContent, false);
                remoteViews.reapply(context, view);
                mContent.addView(view);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        mContent = findViewById(R.id.ll);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.utte.MYBroadcast");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void onClick(View view) {
        startActivity(new Intent(AActivity.this, BActivity.class));
    }
}
