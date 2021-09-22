package com.android.base.language;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.Locale;

public class LanguagesManager {

    /**
     * 初始化国际化框架
     */
    public static void init(Application application) {
        LanguagesChange.register(application);
    }

    /**
     * 在上下文的子类中重写 attachBaseContext 方法（用于更新 Context 的语种）
     */
    public static Context attach(Context context) {
        if (!LanguagesUtils.equalsLanguages(context, getAppLanguage(context))) {
            return LanguagesUtils.updateLanguages(context, getAppLanguage(context));
        }
        return context;
    }

    /**
     * 设置App的语言
     */
    public static boolean setAppLanguage(Context context, Locale locale) {
        if (!getAppLanguage(context).equals(locale)) {
            LanguagesPreferences.getInstance(context).setAppLanguage(locale);
            LanguagesUtils.setApplicationLanguage(context, locale);
            return true;
        }
        return false;
    }

    /**
     * 获取App的语言
     */
    public static Locale getAppLanguage(Context context) {
        return LanguagesPreferences.getInstance(context).getAppLanguage();
    }

    /**
     * 将 App 语种设置为系统语言
     */
    public static boolean setSystemLanguage(Context context) {
        LanguagesPreferences.getInstance(context).clearAppLanguage();
        if (!LanguagesUtils.equalsLanguages(context, getSystemLanguage())) {
            LanguagesUtils.updateLanguages(context, getSystemLanguage());
            // 需要重启
            return true;
        }
        // 不需要重启
        return false;
    }

    /**
     * 获取系统的语言
     */
    public static Locale getSystemLanguage() {
        return LanguagesChange.getSystemLanguage();
    }

    /**
     * 获取某个语言下的 String
     */
    public static String getLanguageString(Context context, Locale locale, int id) {
        return getLanguageResources(context, locale).getString(id);
    }

    /**
     * 获取某个语言下的Resources对象
     */
    public static Resources getLanguageResources(Context context, Locale locale) {
        return LanguagesUtils.getLanguageResources(context, locale);
    }

    /**
     * 获取Resources 对象
     */
    public static Resources getAppLanguageResources(Context context) {
        return LanguagesUtils.getAppLanguageResources(context, getAppLanguage(context));
    }

}
