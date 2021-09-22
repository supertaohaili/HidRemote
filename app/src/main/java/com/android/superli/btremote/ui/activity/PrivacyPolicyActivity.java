package com.android.superli.btremote.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

public class PrivacyPolicyActivity extends XActivity implements View.OnClickListener {

    private TextView tv_msg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    public int getActivityTitle() {
        return R.string.privacy_policy;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)

                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        tv_msg = findViewById(R.id.tv_msg);

        tv_msg.setText("我们尊重并保护您的个人隐私！\n" +
                "\n" +
                "1. 服务条款的确认和接受\n" +
                "使用本应用既表示您同意接受本协议的所有条款。\n" +
                "\n" +
                "2、关于敏感权限\n" +
//                "一、存储读写权限：该权限仅用于友情反馈中，当你需要选择图片时被使用；\n" +
//                "二、拍摄照片权限：该权限仅用于友情反馈中，当你需要拍摄照片时被使用；\n" +
                "一、定位权限：该权限仅用于蓝牙扫描（android 6.0 之后需要同意应用获取定位方可扫描出蓝牙设备）;\n" +
                "\n" +
                "3. 服务条款的变更和修改\n" +
                "我们在必要时会修改服务条款，服务条款一旦发生变更，将会在重要页面上提示修改内容。如果您继续享用本应用的信息服务，则视为接受服务条款的变更。本应用保留随时修改或中断服务而不需要通知您的权利。本应用行使修改或中断服务的权利，不需对你或第三方负责。\n" +
                "\n" +
                "总之，我们将用一些合法的方法去保护您的个人隐私。\n" +
                "\n");
    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_menu:
                break;
        }

    }
}