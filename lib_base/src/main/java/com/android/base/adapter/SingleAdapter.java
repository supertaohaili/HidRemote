package com.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 单布局的Adapter
 * ---------------
 * 数据类型是泛型
 */
public abstract class SingleAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    protected List<T> items = new ArrayList<>();
    private int layoutId;

    public SingleAdapter(Context context, int layoutId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }


    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperViewHolder(inflater.inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        bindData(holder, items.get(position));
        bindData(holder, items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Context getContext() {
        return context;
    }

    public void setData(List<T> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    public boolean isHaveData() {
        if (this.items == null || this.items.size() < 1) {
            return false;
        }
        return true;
    }

    public void addData(T list) {
        this.items.add(list);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public T getData(int position) {
        if (items == null || position >= items.size()) {
            return null;
        }
        return this.items.get(position);
    }

    public List<T> getData() {
        return items;
    }

    protected void bindData(SuperViewHolder holder, T item) {
    }

    protected void bindData(SuperViewHolder holder, T item, int position) {
    }

}
