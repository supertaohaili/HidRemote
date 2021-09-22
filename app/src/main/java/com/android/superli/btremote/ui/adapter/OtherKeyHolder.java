package com.android.superli.btremote.ui.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.android.base.adapter.SingleAdapter;
import com.android.base.adapter.SuperViewHolder;
import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.KeyBean;
import com.android.superli.btremote.config.RemoteApplication;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.KeyConfigs;
import com.android.superli.btremote.utils.VibrateUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OtherKeyHolder extends RecyclerView.ViewHolder {

    private RecyclerView mRecyclerview;
    private MenuAdpter mMenuAdpter;

    public OtherKeyHolder(@NonNull View view, Context context) {
        super(view);
        mRecyclerview = view.findViewById(R.id.recyclerview);
        mMenuAdpter = new MenuAdpter(context);
        mRecyclerview.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(context, 3);
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mRecyclerview.setAdapter(mMenuAdpter);
        mMenuAdpter.setData(KeyConfigs.getOtherKey());
    }


    public class MenuAdpter extends SingleAdapter<KeyBean> {
        public MenuAdpter(Context context) {
            super(context, R.layout.item_remote_tool);
        }

        @Override
        protected void bindData(SuperViewHolder holder, KeyBean item, int position) {
            super.bindData(holder, item, position);
            Button tv_tool = holder.getView(R.id.tv_tool);
            tv_tool.setText(item.name);
            tv_tool.setTag(item.key);
            tv_tool.setOnTouchListener(onTouchListener);
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
