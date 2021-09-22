package com.android.superli.btremote.ui.activity.tool;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.KeyConfigs;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.VibrateUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class RemoteActivity extends XActivity implements View.OnClickListener {

    private LinearLayout llt_remote;
    private LinearLayout llt_num;
    private LinearLayout llt_tool;

    private TextView tv_remote_key;
    private TextView tv_cc_key;
    private TextView tv_num_key;


    @Override
    public int getLayoutId() {
        return R.layout.activity_control_remote;
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

        llt_remote = findViewById(R.id.llt_remote);
        llt_num = findViewById(R.id.llt_num);
        llt_tool = findViewById(R.id.llt_tool);

        tv_remote_key = findViewById(R.id.tv_remote_key);
        tv_cc_key = findViewById(R.id.tv_cc_key);
        tv_num_key = findViewById(R.id.tv_num_key);
        tv_remote_key.setOnClickListener(this);
        tv_num_key.setOnClickListener(this);
        tv_cc_key.setOnClickListener(this);

        llt_remote.setVisibility(View.VISIBLE);
        llt_num.setVisibility(View.GONE);
        llt_tool.setVisibility(View.GONE);

        tv_remote_key.setTextColor(getResources().getColor(R.color.main_color));
        tv_cc_key.setTextColor(getResources().getColor(R.color.main_text));
        tv_num_key.setTextColor(getResources().getColor(R.color.main_text));


        List<KeyBean> keys = KeyConfigs.getKeys3();
        for (KeyBean bean : keys) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }

        List<KeyBean> keys2 = KeyConfigs.getKeys4();
        for (KeyBean bean : keys2) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener2);
        }

        List<KeyBean> keys3 = KeyConfigs.getKeys5();
        for (KeyBean bean : keys3) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener3);
        }

        List<KeyBean> keys5 = KeyConfigs.getNumKeys();
        for (KeyBean bean : keys5) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }

        List<KeyBean> keys6 = KeyConfigs.getOtherKey2();
        for (KeyBean bean : keys6) {
            View vid = findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }


    }


    View.OnTouchListener onTouchListener3 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                HidConsts.KbdKeyDown(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_remote_unkey);
                VibrateUtil.vibrate();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                HidConsts.KbdKeyUp(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_remote_key);
            }
            return true;
        }
    };


    View.OnTouchListener onTouchListener2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                HidConsts.KbdKeyDown(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_oval_ungray);
                VibrateUtil.vibrate();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                HidConsts.KbdKeyUp(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_oval_gray);
            }
            return true;
        }
    };

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
            return true;
        }
    };

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_remote_key) {
            llt_remote.setVisibility(View.VISIBLE);
            llt_num.setVisibility(View.GONE);
            llt_tool.setVisibility(View.GONE);
            tv_remote_key.setTextColor(getResources().getColor(R.color.main_color));
            tv_cc_key.setTextColor(getResources().getColor(R.color.main_text));
            tv_num_key.setTextColor(getResources().getColor(R.color.main_text));

        } else if (id == R.id.tv_num_key) {
            llt_remote.setVisibility(View.GONE);
            llt_num.setVisibility(View.VISIBLE);
            llt_tool.setVisibility(View.GONE);
            tv_remote_key.setTextColor(getResources().getColor(R.color.main_text));
            tv_cc_key.setTextColor(getResources().getColor(R.color.main_text));
            tv_num_key.setTextColor(getResources().getColor(R.color.main_color));
        } else if (id == R.id.tv_cc_key) {
            llt_remote.setVisibility(View.GONE);
            llt_num.setVisibility(View.GONE);
            llt_tool.setVisibility(View.VISIBLE);
            tv_remote_key.setTextColor(getResources().getColor(R.color.main_text));
            tv_cc_key.setTextColor(getResources().getColor(R.color.main_color));
            tv_num_key.setTextColor(getResources().getColor(R.color.main_text));
        }
    }


}