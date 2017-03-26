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
import com.handsomexu.qianzhi.bean.GuokrNews;
import com.handsomexu.qianzhi.interfaces.GuokrContract;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.util.NetworkState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class GuokrPresenter implements GuokrContract.Presenter {
    private Context mContext;
    private GuokrContract.View mView;
    private StringModelImpl mModel;

    private ArrayList<GuokrNews.Result> mList;
    private Gson mGson;


    public GuokrPresenter(Context context, GuokrContract.View mView) {
        this.mContext = context;
        this.mView = mView;
        mView.setPresenter(this);
        mModel = new StringModelImpl(context);
        mList = new ArrayList<>();
        mGson = new Gson();
    }


    @Override
    public void start() {
        loadPosts();
    }

    @Override
    public void loadPosts() {
        mView.showLoading();

        if (NetworkState.networkConnected(mContext)) {
            mModel.load(Api.GUOKR_ARTICLES, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    mList.clear();
                    try {
                        GuokrNews news = mGson.fromJson(result, GuokrNews.class);
                        for (GuokrNews.Result item : news.getResult()) {
                            mList.add(item);
                        }
                        mView.showResults(mList);
                    } catch (JsonSyntaxException e) {
                        mView.showError();
                    }
                    mView.stopLoading();
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
        loadPosts();
    }

    @Override
    public void startReading(int position) {
        GuokrNews.Result item = mList.get(position);
        mContext.startActivity(new Intent(mContext, DetailActivity.class)
                .putExtra("type", BeanType.GUOKR)
                .putExtra("id", item.getId())
                .putExtra("coverUrl", item.getHeadline_img_tb())
                .putExtra("title", item.getTitle()));
    }

    @Override
    public void feelLucky() {
        if (mList.isEmpty()) {
            mView.showError();
        }
        startReading(new Random().nextInt(mList.size()));

    }
}
