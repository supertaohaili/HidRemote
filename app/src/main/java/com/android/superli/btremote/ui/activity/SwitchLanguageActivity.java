package com.android.superli.btremote.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.base.language.LanguagesManager;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.Locale;

public class SwitchLanguageActivity extends XActivity implements View.OnClickListener {

    private LinearLayout mLltZhongwen;
    private ImageView mIvZhongwen;
    private LinearLayout mLltEnglish;
    private ImageView mIvEnglish;

    @Override
    public int getLayoutId() {
        return R.layout.activity_switch_language;
    }

    @Override
    public int getActivityTitle() {
        return R.string.switch_language;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();
        mLltZhongwen = findViewById(R.id.llt_zhongwen);
        mIvZhongwen = findViewById(R.id.iv_zhongwen);
        mLltEnglish = findViewById(R.id.llt_english);
        mIvEnglish = findViewById(R.id.iv_english);
        mLltEnglish.setOnClickListener(this);
        mLltZhongwen.setOnClickListener(this);
    }


    @Override
    public void initData() {
        Locale appLanguage = LanguagesManager.getAppLanguage(this);
        if (appLanguage.equals(Locale.CHINA)) {
            mIvZhongwen.setVisibility(View.VISIBLE);
            mIvEnglish.setVisibility(View.GONE);
        } else {
            mIvZhongwen.setVisibility(View.GONE);
            mIvEnglish.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        boolean restart = false;
        switch (v.getId()) {
            case R.id.llt_zhongwen:
                restart = LanguagesManager.setAppLanguage(this, Locale.CHINA);
                break;
            case R.id.llt_english:
                restart = LanguagesManager.setAppLanguage(this, Locale.ENGLISH);
                break;
        }
        if (restart) {
            Locale appLanguage = LanguagesManager.getAppLanguage(this);
            if (appLanguage.equals(Locale.CHINA)) {
                mIvZhongwen.setVisibility(View.VISIBLE);
                mIvEnglish.setVisibility(View.GONE);
            } else {
                mIvZhongwen.setVisibility(View.GONE);
                mIvEnglish.setVisibility(View.VISIBLE);
            }
        }
    }
}