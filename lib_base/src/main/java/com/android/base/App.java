package com.android.base;

import android.app.Application;


public abstract class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化配置
        try {
            initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void initConfig() throws Exception;

}
