package com.todou.recycleritemanimatorstest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todou.recycleritemanimatorstest.R;
public class TestRecyclerAdapter extends RecyclerViewAdapter<String> {

    public TestRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(String data, int i, RecyclerView.ViewHolder viewHolder) {
        TextViewHolder holder = (TextViewHolder) viewHolder;
        holder.bindView(data);
        holder.itemView.setVisibility(View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = getLayoutInflator();
        View view = inflater.inflate(R.layout.list_item_view, viewGroup, false);
        return new TextViewHolder(view);
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public TextViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bindView(String s) {
            text.setText(s);
        }
    }
}
