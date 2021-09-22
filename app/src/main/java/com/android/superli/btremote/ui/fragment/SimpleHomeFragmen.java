package com.android.superli.btremote.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.adapter.DataHolder;
import com.android.base.adapter.LayoutWrapper;
import com.android.base.adapter.SuperAdapter;
import com.android.base.adapter.SuperViewHolder;
import com.android.base.router.Router;
import com.android.base.ui.XFragment;
import com.android.superli.btremote.R;
import com.android.superli.btremote.bean.Tool;
import com.android.superli.btremote.ui.activity.tool.KeyboardActivity;
import com.android.superli.btremote.ui.activity.tool.MouseActivity;
import com.android.superli.btremote.ui.activity.tool.PptActivity;
import com.android.superli.btremote.ui.activity.tool.RemoteActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleHomeFragmen extends XFragment {

    private RecyclerView mRecyclerview;
    private List<LayoutWrapper> mLayoutWrappers;
    private SuperAdapter mSuperAdapter;
    private DataHolder<Tool> toolDataHolder;

    public static SimpleHomeFragmen newInstance() {
        SimpleHomeFragmen fragment = new SimpleHomeFragmen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_simple;
    }

    @Override
    public void bindUI(View rootView) {
        mRecyclerview = rootView.findViewById(R.id.recyclerview);
        int[] layoutIds = {R.layout.item_tool_simple};
        mSuperAdapter = new SuperAdapter(getActivity(), layoutIds);
        mRecyclerview.setHasFixedSize(true);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLayoutWrappers.get(position).getSpanSize();
            }
        });
        mRecyclerview.setLayoutManager(mGridLayoutManager);
        mRecyclerview.setAdapter(mSuperAdapter);
        mLayoutWrappers = new ArrayList<>();


        toolDataHolder = new DataHolder<Tool>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, Tool item, int position) {

                TextView tv_tool = holder.getView(R.id.tv_tool);
                ImageView iv_tool = holder.getView(R.id.iv_tool);
                tv_tool.setText(item.name);
                iv_tool.setBackgroundResource(item.icon);
                holder.getView(R.id.llt_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.newIntent(getActivity())
                                .to(item.aclass)
                                .launch();
                    }
                });
            }
        };

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mLayoutWrappers.add(new LayoutWrapper(R.layout.item_tool_simple, 1, new Tool(R.drawable.icon_key, "键盘", KeyboardActivity.class), toolDataHolder));
        mLayoutWrappers.add(new LayoutWrapper(R.layout.item_tool_simple, 1, new Tool(R.drawable.icon_mouse, "鼠标", MouseActivity.class), toolDataHolder));
        mLayoutWrappers.add(new LayoutWrapper(R.layout.item_tool_simple, 1, new Tool(R.drawable.icon_ppt, "ppt工具", PptActivity.class), toolDataHolder));
        mLayoutWrappers.add(new LayoutWrapper(R.layout.item_tool_simple, 1, new Tool(R.drawable.icon_remote, "无线遥控器", RemoteActivity.class), toolDataHolder));
        mSuperAdapter.setData(mLayoutWrappers);
    }


}
