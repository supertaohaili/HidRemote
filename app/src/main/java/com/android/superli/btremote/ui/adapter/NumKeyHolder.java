package com.android.superli.btremote.ui.adapter;

import android.view.MotionEvent;
import android.view.View;

import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;
import com.android.superli.btremote.hid.KeyConfigs;
import com.android.superli.btremote.config.RemoteApplication;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.utils.VibrateUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NumKeyHolder extends RecyclerView.ViewHolder {

    public NumKeyHolder(@NonNull View view) {
        super(view);
        List<KeyBean> keys = KeyConfigs.getNumKeys();
        for (KeyBean bean : keys) {
            View vid = view.findViewById(bean.vid);
            vid.setTag(bean.key);
            vid.setOnTouchListener(onTouchListener);
        }
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
}
