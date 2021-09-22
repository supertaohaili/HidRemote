package com.android.superli.btremote.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context) {
       Utils.context = context;
    }

    public static Context getContext() {
        if (Utils.context != null) return Utils.context;
        throw new NullPointerException("u should init first");
    }


    public static String getAppMsg() {
        return getAppVersionCode() + "/"
                + getAppVersionName();
    }

    public static String getPhoneMsg() {
        return android.os.Build.BRAND + "/"
                + android.os.Build.MODEL + "/"
                + "anroid " + android.os.Build.VERSION.RELEASE;
    }

    private static int getAppVersionCode() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String getAppVersionName() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}