package com.android.base.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.android.base.Config;

import java.io.File;

public interface ILoader {

    void init(Context context);

    void loadNet(ImageView target, String url, Options options);

    void loadNet(Context context, String url, Options options, LoadCallback callback);

    void loadResource(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);

    void loadCircle(String url, ImageView target, Options options);

    void loadCorner(String url, ImageView target, int radius, Options options);

    class Options {

        public static final int RES_NONE = Config.IL_ERROR_RES;

        public int loadingResId = RES_NONE;        //加载中的资源id
        public int loadErrorResId = RES_NONE;      //加载失败的资源id
        public ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;



        public static Options defaultOptions() {
            return new Options(Config.IL_LOADING_RES, Config.IL_ERROR_RES);
        }

        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }

        public Options scaleType(ImageView.ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }
    }

}
