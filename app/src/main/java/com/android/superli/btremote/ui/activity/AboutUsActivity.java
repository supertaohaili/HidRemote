package com.android.superli.btremote.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.base.SharedPreferencesUtil;
import com.gyf.immersionbar.ImmersionBar;

public class AboutUsActivity extends XActivity implements View.OnClickListener {

    private TextView tv_app_msg;
    private TextView tv_msg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public int getActivityTitle() {
        return R.string.about_us;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this).titleBar(R.id.llt_title)
                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

//        TextView tv_menu = findViewById(R.id.tv_menu);
//        tv_menu.setVisibility(View.VISIBLE);
//        tv_menu.setText("分享APP");
//        tv_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareTextIntent = IntentUtils.getShareTextIntent("https://www.coolapk.com/apk/com.android.video.thlplayer");
//                startActivity(shareTextIntent);
//            }
//        });

        tv_app_msg = findViewById(R.id.tv_app_msg);
        tv_msg = findViewById(R.id.tv_msg);

        tv_app_msg.setText(getAppVersionName() + "");
        tv_msg.setText("没有太晚的开始，不如就从今天行动！");

    }


    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }

    public String getAppVersionName() {
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}