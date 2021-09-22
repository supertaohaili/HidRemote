package com.android.superli.btremote.ui.activity.tool;

import android.view.MotionEvent;
import android.view.View;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;
import com.android.superli.btremote.hid.KeyConfigs;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.ui.views.MouseDialog;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.VibrateUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class KeyboardActivity extends XActivity implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_control_keyboard;
    }

    @Override
    public int getActivityTitle() {
        return R.string.device_edit;
    }


    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);
        ImmersionBar.with(this)
                .titleBar(R.id.llt_content)
                .statusBarDarkFont(theme == 0 ? true : false, 0.2f)
                .keyboardEnable(true)
                .init();

        List<KeyBean> keys = KeyConfigs.getKeys();
        for (KeyBean bean : keys) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }

        findViewById(R.id.tv_fn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMouseDialog();
            }
        });

        HidConsts.CleanKbd();
    }

    private MouseDialog mMouseDialog;

    private void showMouseDialog() {
        if (mMouseDialog == null) {
            mMouseDialog = new MouseDialog(this);
        }
        mMouseDialog.show();
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                HidConsts.KbdKeyDown(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                VibrateUtil.vibrate();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                HidConsts.KbdKeyUp(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_key_sel_c5);
            }
            return false;
        }
    };

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }
}