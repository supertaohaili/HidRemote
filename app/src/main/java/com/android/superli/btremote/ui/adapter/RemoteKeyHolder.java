package com.android.superli.btremote.ui.adapter;

import android.view.MotionEvent;
import android.view.View;

import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;
import com.android.superli.btremote.config.RemoteApplication;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.KeyConfigs;
import com.android.superli.btremote.utils.VibrateUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RemoteKeyHolder extends RecyclerView.ViewHolder {

    public RemoteKeyHolder(@NonNull View view) {
        super(view);
        List<KeyBean> keys = KeyConfigs.getKeys3();
        for (KeyBean bean : keys) {
            View vid = view.findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }

        List<KeyBean> keys2 = KeyConfigs.getKeys4();
        for (KeyBean bean : keys2) {
            View vid = view.findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener2);
        }

        List<KeyBean> keys3 = KeyConfigs.getKeys5();
        for (KeyBean bean : keys3) {
            View vid = view.findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener3);
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
}
