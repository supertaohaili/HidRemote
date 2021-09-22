package com.android.base.language;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class LanguagesPreferences {
    private static volatile LanguagesPreferences instance;
    private final String SP_NAME = "language_setting";
    private final String KEY_LANGUAGE = "key_language";
    private final String KEY_COUNTRY = "key_country";
    private final SharedPreferences mSharedPreferences;
    private Locale currentLocal = null;

    public static LanguagesPreferences getInstance(Context context) {
        if (instance == null) {
            synchronized (LanguagesPreferences.class) {
                if (instance == null) {
                    instance = new LanguagesPreferences(context);
                }
            }
        }
        return instance;
    }

    public LanguagesPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    //保存APP语言
    public void setAppLanguage(Locale locale) {
        currentLocal = locale;
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(KEY_LANGUAGE, locale.getLanguage());
        edit.putString(KEY_COUNTRY, locale.getCountry());
        edit.apply();
    }

    //获取APP语言
    public Locale getAppLanguage() {
        if (currentLocal == null) {
            String language = mSharedPreferences.getString(KEY_LANGUAGE, null);
            String country = mSharedPreferences.getString(KEY_COUNTRY, null);
            if (language != null && !language.equals("")) {
                currentLocal = new Locale(language, country);
            } else {
                currentLocal = Locale.getDefault();
            }
        }
        return currentLocal;
    }

    //清空APP语言
    public void clearAppLanguage() {
        currentLocal = LanguagesChange.getSystemLanguage();
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.remove(KEY_LANGUAGE);
        edit.remove(KEY_COUNTRY);
        edit.apply();
    }

}
