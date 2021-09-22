package com.android.superli.btremote.ui.activity.fqs;

import android.view.View;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

public class FqsMsgActivity extends XActivity implements View.OnClickListener {



    @Override
    public int getLayoutId() {
        return R.layout.activity_fqs_msg;
    }

    @Override
    public int getActivityTitle() {
        return R.string.common_problem;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();
    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }


}