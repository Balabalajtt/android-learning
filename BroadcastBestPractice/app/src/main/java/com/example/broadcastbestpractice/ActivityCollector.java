package com.example.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/26.
 */

public class ActivityCollector {

    public static List<Activity> sActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        sActivities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : sActivities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
