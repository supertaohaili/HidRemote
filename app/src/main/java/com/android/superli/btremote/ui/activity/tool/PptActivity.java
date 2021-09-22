package com.android.superli.btremote.ui.activity.tool;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.android.superli.btremote.hid.HidConsts;
import com.android.base.SharedPreferencesUtil;
import com.android.superli.btremote.utils.VibrateUtil;
import com.gyf.immersionbar.ImmersionBar;

public class PptActivity extends XActivity implements View.OnClickListener {

    private Button btn_shang;
    private Button btn_xia;
    private Button tv_quanping;
    private Button tv_tuichuquanping;

    @Override
    public int getLayoutId() {
        return R.layout.activity_control_ppt;
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

        btn_shang = findViewById(R.id.btn_shang);
        btn_shang.setTag("82");
        btn_shang.setOnTouchListener(onTouchListener);

        btn_xia = findViewById(R.id.btn_xia);
        btn_xia.setTag("81");
        btn_xia.setOnTouchListener(onTouchListener);

        tv_quanping = findViewById(R.id.tv_quanping);
        tv_quanping.setTag("62");
        tv_quanping.setOnTouchListener(onTouchListener);

        tv_tuichuquanping = findViewById(R.id.tv_tuichuquanping);
        tv_tuichuquanping.setTag("41");
        tv_tuichuquanping.setOnTouchListener(onTouchListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                HidConsts.KbdKeyDown(v.getTag().toString());
                v.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                VibrateUtil.vibrate();
            }else if(event.getAction() == MotionEvent.ACTION_UP){
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