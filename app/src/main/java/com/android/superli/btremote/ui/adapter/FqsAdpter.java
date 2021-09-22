package com.android.superli.btremote.ui.adapter;

import android.content.Context;
import android.view.View;

import com.android.base.adapter.SingleAdapter;
import com.android.base.adapter.SuperViewHolder;
import com.android.superli.btremote.R;

/**
 * @ClassName: AfterSalesAdpter
 * @Description: java类作用描述
 * @Author: taohaili
 * @CreateDate: 2021/1/21 14:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2021/1/21 14:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class FqsAdpter extends SingleAdapter<String> {
    public FqsAdpter(Context context) {
        super(context, R.layout.item_fqs);
    }

    @Override
    protected void bindData(SuperViewHolder holder, String item, int position) {
        super.bindData(holder, item, position);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onClick(item);
                }

            }
        });
    }

    private OnClickListener mOnClickListener;

    public void setmOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener{
        void onClick(String item);
    }
}
