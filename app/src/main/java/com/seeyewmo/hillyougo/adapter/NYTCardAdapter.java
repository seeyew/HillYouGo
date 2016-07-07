package com.seeyewmo.hillyougo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.model.Result;

import java.util.ArrayList;
import java.util.List;

public class NYTCardAdapter extends RecyclerView.Adapter<NYTCardAdapter.ViewHolder> {
    List<Result> mItems;

    public NYTCardAdapter() {
        super();
        mItems = new ArrayList<Result>();
        /*Github g1 = new Github();
        g1.setLogin("Test");
        g1.setBlog("Blog");
        Github g2 = new Github();
        g2.setLogin("Test2");
        g2.setBlog("Blog2");
        mItems.add(g1);
        mItems.add(g2);*/
    }

    public void addData(Result result) {
        mItems.add(result);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Result result = mItems.get(i);
        viewHolder.login.setText(result.getTitle());
        viewHolder.repos.setText(result.getAbstract());
        viewHolder.blog.setText(result.getUrl());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView repos;
        public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }
    }
}
