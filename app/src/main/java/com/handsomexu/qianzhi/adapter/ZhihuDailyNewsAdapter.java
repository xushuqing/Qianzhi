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
import com.handsomexu.qianzhi.bean.ZhihuDailyNews;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ZhihuDailyNews.Story> mList;
    private LayoutInflater mInflater;
    private static final int NORNAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;
    private OnRecyclerViewOnClickListener mListener;

    public ZhihuDailyNewsAdapter(Context context, List<ZhihuDailyNews.Story> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORNAL_TYPE) {
            View view = mInflater.inflate(R.layout.home_list_item_layout, parent, false);
            NormalViewHolder normalViewHolder = new NormalViewHolder(view);
            return normalViewHolder;
        } else if (viewType == FOOTER_TYPE) {
            View view = mInflater.inflate(R.layout.list_footer, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            ZhihuDailyNews.Story story = mList.get(position);
            if (story.getImages().get(0) != null) {
                Glide.with(mContext)
                        .load(story.getImages().get(0))
                        .asBitmap()
                        .placeholder(R.mipmap.expression)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.error)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).imageView);
            } else {
                (((NormalViewHolder) holder).imageView).setVisibility(View.GONE);
            }
            ((NormalViewHolder) (holder)).textView.setText(story.getTitle());
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
        }
        return NORNAL_TYPE;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.mListener = listener;

    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(this.getLayoutPosition());
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
