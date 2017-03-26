package com.handsomexu.qianzhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.bean.GuokrNews;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/20.
 */

public class GuokrAdapter extends RecyclerView.Adapter<GuokrAdapter.Holder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GuokrNews.Result> mList;

    private OnRecyclerViewOnClickListener mListener;

    public GuokrAdapter(Context context, ArrayList<GuokrNews.Result> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        GuokrNews.Result item = mList.get(position);
        Glide.with(mContext)
                .load(item.getHeadline_img_tb())
                .asBitmap()
                .placeholder(R.mipmap.expression)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.error)
                .centerCrop()
                .into(holder.imageView);

        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getLayoutPosition());
            }
        }
    }

    public void setOnItemClickListener(OnRecyclerViewOnClickListener listener) {
        mListener = listener;
    }
}
