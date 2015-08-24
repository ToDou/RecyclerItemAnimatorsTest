package com.todou.recycleritemanimatorstest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import java.util.ArrayList;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflator;

    private ArrayList<T> mData;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        mInflator = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void updateData(ArrayList<T> data) {
        setData(data);
        notifyDataSetChanged();
    }

    private void setData(ArrayList<T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
    }

    public LayoutInflater getLayoutInflator() {
        return mInflator;
    }

    public Context getContext() {
        return mContext;
    }

    private T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        T data = getItem(i);
        bindView(data, i, viewHolder);
    }

    public abstract void bindView(T data, int i, RecyclerView.ViewHolder viewHolder);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
