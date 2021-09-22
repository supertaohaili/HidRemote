package com.android.superli.btremote.config;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity 栈管理
 */
public class ActivityTack {

    public List<Activity> activityList = new ArrayList<Activity>();

    public static ActivityTack tack = new ActivityTack();

    private ActivityTack() {}

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }
}