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

    private Context context;
    private List<ZhihuDailyNews.Story> storyList;
    private LayoutInflater inflater;
    private static final int NORNAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;
    private OnRecyclerViewOnClickListener mListener;

    public ZhihuDailyNewsAdapter(Context context, List<ZhihuDailyNews.Story> storyList) {
        this.context = context;
        this.storyList = storyList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORNAL_TYPE) {
            View view = inflater.inflate(R.layout.home_list_item_layout, parent, false);
            NormalViewHolder normalViewHolder = new NormalViewHolder(view);
            return normalViewHolder;
        } else if (viewType == FOOTER_TYPE) {
            View view = inflater.inflate(R.layout.list_footer, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalViewHolder){
            ZhihuDailyNews.Story story =storyList.get(position);
            if(story.getImages().get(0) != null){
               // Glide.with(context).load(story.getImages().get(0)).into(((NormalViewHolder)holder).imageView);
                Glide.with(context)
                        .load(story.getImages().get(0))
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(((NormalViewHolder)holder).imageView);
            }else {
                (((NormalViewHolder)holder).imageView).setVisibility(View.GONE);
            }
            ((NormalViewHolder)( holder)).textView.setText(story.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == storyList.size()) {
            return FOOTER_TYPE;
        }
        return NORNAL_TYPE;
    }
    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;

    }
    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;
        OnRecyclerViewOnClickListener listener;
        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onItemClick(v,getLayoutPosition());
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
