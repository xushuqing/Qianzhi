package com.handsomexu.qianzhi.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.handsomexu.qianzhi.ClassImpl.StringModelImpl;
import com.handsomexu.qianzhi.api.Api;
import com.handsomexu.qianzhi.bean.ZhihuDailyNews;
import com.handsomexu.qianzhi.fragments.ZhihuDailyFragment;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.interfaces.ZhihuDailyContract;
import com.handsomexu.qianzhi.util.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private Context context;
    private StringModelImpl model;
    private DateFormatter formatter;
    private ArrayList<ZhihuDailyNews.Story> mNewsList;
    private ZhihuDailyContract.View view;
    private static final String TAG = "ZhihuDailyPresenter";
    private Gson gson = new Gson();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.context = context;
        model = new StringModelImpl(context);
        formatter = new DateFormatter();
        mNewsList = new ArrayList<>();
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void start() {
     loadPosts(System.currentTimeMillis(),false);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        String url = Api.ZHIHU_HISTORY + formatter.ZhihuDateFormat(date);
        Log.e(TAG, "url---------- "+url);
        model.load(url, new OnStringListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    ZhihuDailyNews newsBean = gson.fromJson(result, ZhihuDailyNews.class);
                    if (clearing) {
                        mNewsList.clear();
                    } else {
                        for (ZhihuDailyNews.Story story : newsBean.getStories()) {
                            mNewsList.add(story);
                        }
                        view.showResults(mNewsList);
                    }
                } catch (JsonSyntaxException e){
                    view.showError();
                }
                view.stopLoading();
            }

            @Override
            public void onFailure(VolleyError error) {
                view.stopLoading();
                view.showError();
            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void startReading(int position) {
        ZhihuDailyNews.Story item = mNewsList.get(position);
       // context.startActivity(new Intent(context,DetailActivity.class));
    }

    @Override
    public void feelLucky() {

    }
}
