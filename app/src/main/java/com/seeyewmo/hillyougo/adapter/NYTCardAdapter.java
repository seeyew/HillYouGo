package com.seeyewmo.hillyougo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.model.Result;
import com.seeyewmo.hillyougo.ui.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class NYTCardAdapter extends RecyclerView.Adapter<NYTCardAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position, Result item);
    }
    List<Result> mItems;
    OnItemClickListener mOnItemClickListener;

    public NYTCardAdapter(OnItemClickListener listener) {
        super();
        mOnItemClickListener = listener;
        mItems = new ArrayList<Result>();
    }

    public void addAllData(List<Result> results) {
        mItems.clear();
        mItems.addAll(results);
        notifyDataSetChanged();
    }

   /* public void addData(Result result) {
        mItems.add(result);
        notifyDataSetChanged();
    }*/

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Result result = mItems.get(i);

        viewHolder.bind(i,result, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public SimpleDraweeView draweeView;
        public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.recycler_view_image);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }

        public void bind(final int position, final Result result, final OnItemClickListener listener) {
            login.setText(result.getTitle());
            draweeView.setImageURI(ImageUtil.getBestPhotoUrlForArticle(result));
            blog.setText(result.getPublishedDate());

            //Todo Let's use Picasso for pictures!
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(position, result);
                }
            });
        }
    }
}
