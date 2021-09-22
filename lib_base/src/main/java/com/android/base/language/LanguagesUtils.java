package com.android.base.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguagesUtils {

    /**
     * 判断上下文的语种和某个语种是否相同
     */
    static boolean equalsLanguages(Context context, Locale locale) {
        Configuration config = context.getResources().getConfiguration();
        // API 版本兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0).equals(locale);
        } else {
            return config.locale.equals(locale);
        }
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context, Locale locale) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //如果API < 17
            config.locale = locale;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            //如果 17 < = API < 25 Android 7.7.1
            config.setLocale(locale);
        } else {
            //API 25  Android 7.7.1
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocale(locale);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
        }
        Locale.setDefault(locale);
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取Resources 对象
     */
    static Resources getAppLanguageResources(Context context, Locale locale) {
        Context appContext = context.getApplicationContext();
        Configuration configuration = appContext.getResources().getConfiguration();
        Resources resources = appContext.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    /**
     * 更新语言设置
     *
     * @param context
     * @param locale
     * @return
     */
    public static Context updateLanguages(Context context, Locale locale) {
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        Locale.setDefault(locale);
        return context;
    }

    /**
     * 获取某个语言下的 Resources 对象
     */
    static Resources getLanguageResources(Context context, Locale locale) {
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            return context.createConfigurationContext(config).getResources();
        } else {
            config.locale = locale;
            return new Resources(context.getAssets(), context.getResources().getDisplayMetrics(), config);
        }
    }

}
