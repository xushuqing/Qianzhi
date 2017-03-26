package com.handsomexu.qianzhi.presenter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handsomexu.qianzhi.Api;
import com.handsomexu.qianzhi.StringModelImpl;
import com.handsomexu.qianzhi.activity.DetailActivity;
import com.handsomexu.qianzhi.bean.BeanType;
import com.handsomexu.qianzhi.bean.DoubanMoment;
import com.handsomexu.qianzhi.fragments.DoubanFragment;
import com.handsomexu.qianzhi.interfaces.DoubanMomentContract;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.util.DateFormatter;
import com.handsomexu.qianzhi.util.NetworkState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class DoubanMomentPresenter implements DoubanMomentContract.Presenter {
    private Context mContext;
    private DoubanMomentContract.View mView;
    private StringModelImpl mModel;
    private ArrayList<DoubanMoment.Post> mList;
    private DateFormatter mDateFormatter;
    private Gson mGson;
    private static final String TAG = "DoubanMomentPresenter";

    public DoubanMomentPresenter(Context context, DoubanFragment doubanFragment) {
        mContext = context;
        mModel = new StringModelImpl(context);
        mList = new ArrayList<>();
        mView = doubanFragment;
        mView.setPresenter(this);
        mDateFormatter = new DateFormatter();
        mGson = new Gson();
    }


    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), false);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
            mView.showLoading();
        }
        if (NetworkState.networkConnected(mContext)) {
            String formatedDate = mDateFormatter.DoubanDateFormat(date);
            mModel.load(Api.DOUBAN_MOMENT + formatedDate, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        DoubanMoment doubanMoment = mGson.fromJson(result, DoubanMoment.class);
                        if (clearing) {
                            mList.clear();
                        }
                        for (DoubanMoment.Post post : doubanMoment.getPosts()) {
                            mList.add(post);
                        }
                        mView.showResults(mList);
                        mView.stopLoading();
                        if (clearing) {
                            mView.showUpdateComplete();
                        }
                    } catch (Exception e) {
                        mView.showError();
                        mView.stopLoading();
                    }
                }

                @Override
                public void onFailure(VolleyError error) {
                    mView.showError();
                }
            });
        } else {
            mView.showError();
        }

    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(mContext, DetailActivity.class);

        if (mList.get(position).getThumbs().size() != 0) {
            String coverUrl = mList.get(position).getThumbs().get(0).getMedium().getUrl();
            intent.putExtra("coverUrl", coverUrl);
        }
        int id = mList.get(position).getId();
        String title = mList.get(position).getTitle();
        intent.putExtra("id", id);
        intent.putExtra("type", BeanType.DOUBAN);
        intent.putExtra("title", title);
        mContext.startActivity(intent);
    }

    @Override
    public void feelLucky() {
        startReading(new Random().nextInt(mList.size()));
    }
}
