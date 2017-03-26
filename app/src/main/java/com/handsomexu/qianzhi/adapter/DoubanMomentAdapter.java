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
import com.handsomexu.qianzhi.bean.DoubanMoment;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/25.
 */

public class DoubanMomentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<DoubanMoment.Post> mList;
    private OnRecyclerViewOnClickListener mListener;
    private LayoutInflater mLayoutInflater;

    private static final int NORNAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;
    private static final int NO_IMG_TYPE = 2;

    public DoubanMomentAdapter(Context context, ArrayList<DoubanMoment.Post> list) {
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORNAL_TYPE:
                View view = mLayoutInflater.inflate(R.layout.home_list_item_layout, parent, false);
                return new NormalViewHolder(view);
            case NO_IMG_TYPE:
                View view1 = mLayoutInflater.inflate(R.layout.home_list_item_layout_noimag, parent, false);
                return new NoImagViewHolder(view1);
            case FOOTER_TYPE:
                View view2 = mLayoutInflater.inflate(R.layout.list_footer, parent, false);
                return new FooterViewHolder(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            if (holder instanceof NormalViewHolder) {
                ((NormalViewHolder) holder).textView.setText(mList.get(position).getTitle());
                Glide.with(mContext)
                        .load(mList.get(position).getThumbs().get(0).getMedium().getUrl())
                        .asBitmap()
                        .centerCrop()
                        .error(R.mipmap.error)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(((NormalViewHolder) holder).imageView);
            } else if (holder instanceof NoImagViewHolder) {
                ((NoImagViewHolder) holder).textView.setText(mList.get(position).getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return FOOTER_TYPE;
        } else if (mList.get(position).getThumbs().size() == 0) {
            return NO_IMG_TYPE;
        }
        return NORNAL_TYPE;
    }

    public void setRecyclerViewOnItemClickListener(OnRecyclerViewOnClickListener listener) {
        mListener = listener;
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getLayoutPosition());
        }
    }

    private class NoImagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public NoImagViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getLayoutPosition());
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
