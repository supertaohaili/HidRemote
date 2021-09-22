package com.android.superli.btremote.config;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.android.base.App;
import com.android.base.language.LanguagesManager;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.HidUitls;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.Utils;
import com.tencent.bugly.Bugly;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;

public class RemoteApplication extends App {

    public static RemoteApplication INSTANCE;

    @Override
    protected void initConfig() throws Exception {

        Utils.init(this);
//        LitePal.initialize(this);
        new HttpConfig().initOkGo(this);
        registerActivityLifecycleCallbacks(new ActLifecycleCallbacks());
        //初始化
        LanguagesManager.init(this);
        SharedPreferencesUtil.init(this);
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance()
                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                    }
                });
        Bugly.init(getApplicationContext(), "6104dc8a82", false);
        UMConfigure.init(this, "600a687df1eb4f3f9b6c0983", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.setLogEnabled(false);
        INSTANCE = this;

        if ( Build.VERSION.SDK_INT>=Build.VERSION_CODES.P) {
            HidUitls.RegistApp(getApplicationContext());
            HidConsts.reporttrans();
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        // 国际化适配（绑定语言）
        super.attachBaseContext(LanguagesManager.attach(base));
    }


}
