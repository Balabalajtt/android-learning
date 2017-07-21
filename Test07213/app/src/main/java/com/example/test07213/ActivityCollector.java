package com.example.test07213;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/7/21.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    public static void finishAll()
    {
        for(Activity activity : activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
        activities.clear();
    }

}
