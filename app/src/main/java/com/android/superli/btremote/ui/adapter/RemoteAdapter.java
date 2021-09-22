package com.android.superli.btremote.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.superli.btremote.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 自定义布局，图片+标题
 */

public class RemoteAdapter extends BannerAdapter<Integer, RecyclerView.ViewHolder> {

    public final static int REMOTE_KEY = 1;
    public final static int NUM_KEY = 2;
    public final static int TOOL_KEY = 3;


    public RemoteAdapter(List<Integer> datas) {
        super(datas);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = position % 3;
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == REMOTE_KEY) {
            return new RemoteKeyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_remote_key, parent, false));
        } else if (viewType == NUM_KEY) {
            return new NumKeyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_num_key, parent, false));
        } else {
            return new OtherKeyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tool_key, parent, false),parent.getContext());
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, Integer data, int position, int size) {

    }
}

