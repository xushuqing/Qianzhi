package com.handsomexu.qianzhi.presenter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.handsomexu.qianzhi.Api;
import com.handsomexu.qianzhi.StringModelImpl;
import com.handsomexu.qianzhi.activity.DetailActivity;
import com.handsomexu.qianzhi.bean.BeanType;
import com.handsomexu.qianzhi.bean.ZhihuDailyNews;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.interfaces.ZhihuDailyContract;
import com.handsomexu.qianzhi.util.DateFormatter;
import com.handsomexu.qianzhi.util.NetworkState;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private Context mContext;
    private StringModelImpl mModel;
    private DateFormatter mFormatter;
    private ArrayList<ZhihuDailyNews.Story> mList;
    private ZhihuDailyContract.View mView;
    private Gson mGson;

    private static final String TAG = "ZhihuDailyPresenter";

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View mView) {
        this.mContext = context;
        mModel = new StringModelImpl(context);
        mFormatter = new DateFormatter();
        mList = new ArrayList<>();
        this.mView = mView;
        mGson = new Gson();
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
            mView.showLoading();
        }
        if (NetworkState.networkConnected(mContext)) {

            String url = Api.ZHIHU_HISTORY + mFormatter.ZhihuDateFormat(date);
            mModel.load(url, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    try {
                        ZhihuDailyNews newsBean = mGson.fromJson(result, ZhihuDailyNews.class);
                        if (clearing) {
                            mList.clear();
                        }
                        for (ZhihuDailyNews.Story story : newsBean.getStories()) {
                            mList.add(story);
                        }
                        mView.showResults(mList);
                        mView.stopLoading();
                        if (clearing) {
                            mView.showUpdateComplete();
                        }
                    } catch (JsonSyntaxException e) {
                        mView.showError();
                    }

                }

                @Override
                public void onFailure(VolleyError error) {
                    mView.stopLoading();
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
        ZhihuDailyNews.Story item = mList.get(position);
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("type", BeanType.ZHIHU)
                .putExtra("id", mList.get(position).getId())
                .putExtra("title", mList.get(position).getTitle())
                .putExtra("coverUrl", mList.get(position).getImages().get(0));
        mContext.startActivity(intent);

    }

    @Override
    public void feelLucky() {
        if (mList.isEmpty()) {
            mView.showError();
            return;
        }
        startReading(new Random().nextInt(mList.size()));

    }
}
