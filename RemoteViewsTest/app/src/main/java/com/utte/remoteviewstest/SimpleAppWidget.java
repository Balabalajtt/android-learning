package com.utte.remoteviewstest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class SimpleAppWidget extends AppWidgetProvider {

    private static final String TAG = "SimpleAppWidget";
    public static final String CLICK = "com.utte.action.CLICK";

    public SimpleAppWidget() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: " + intent.getAction());
        // 如果部件有自己的action，就判断，处理对应的广播
        if (CLICK.equals(intent.getAction())) {
            // 如果是CLICK的action，就开始一个线程去一直更新RemoteViews达到旋转的效果
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                    for (int i = 0; i < 100; i++) {
                        remoteViews.setTextViewText(R.id.tv, i + "");// 设置数字
                        // 调用updateAppWidget去更新RemoteViews
                        manager.updateAppWidget(new ComponentName(context, SimpleAppWidget.class), remoteViews);
                        SystemClock.sleep(18);
                    }
                    remoteViews.setTextViewText(R.id.tv, "RESTART");

                    Intent intent = new Intent(context, SimpleAppWidget.class);
                    intent.setAction(CLICK);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    // 保持Button设置点击事件,触发一个Intent,这里是发送广播
                    remoteViews.setOnClickPendingIntent(R.id.im, pendingIntent);

                    // 调用updateAppWidget去更新RemoteViews
                    manager.updateAppWidget(new ComponentName(context, SimpleAppWidget.class), remoteViews);
                }
            }).start();
        }
    }

    // 部件添加或更新时会调用此方法
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int count = appWidgetIds.length;
        Log.d(TAG, "onUpdate: " + count);
        for (int appWidgetId : appWidgetIds) {
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "onWidgetUpdate: " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intent = new Intent(context, SimpleAppWidget.class);
        intent.setAction(CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        // 保持Button设置点击事件,触发一个Intent,这里是发送广播
        remoteViews.setOnClickPendingIntent(R.id.im, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

}

