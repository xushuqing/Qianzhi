package com.handsomexu.qianzhi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.activity.DetailActivity;
import com.handsomexu.qianzhi.interfaces.DetailContract;

/**
 * Created by HandsomeXu on 2017/3/17.
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private ImageView mImageView;
    private WebView mWebView;
    private NestedScrollView mScrollView;
    private CollapsingToolbarLayout mToolbarLayout;
    private SwipeRefreshLayout mRefresh;

    private Context mContext;

    private DetailContract.Presenter presenter;

    private static final String TAG = "DetailFragment";

    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.universal_read_layout, container, false);
        initView(view);
        setHasOptionsMenu(true);
        presenter.requestData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        } else if (id == R.id.to_top) {
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(NestedScrollView.FOCUS_UP);
                }
            });
        }
        return true;
    }


    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView(View view) {

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        mRefresh.setColorSchemeResources(R.color.colorPrimary);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData();
            }
        });

        mWebView = (WebView) view.findViewById(R.id.web_view);
        mWebView.setScrollbarFadingEnabled(true);

        DetailActivity activity = (DetailActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar_ditail));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView) view.findViewById(R.id.imageView_toolbar);
        mScrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        mToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        //能够和js交互
        mWebView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        mWebView.getSettings().setBuiltInZoomControls(false);
        //缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        mWebView.getSettings().setAppCacheEnabled(false);
    }

    @Override
    public void showLoading() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(mImageView, R.string.fail_please_retry, Snackbar.LENGTH_SHORT).show();
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showResult(String result) {
        mWebView.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null);
    }

    @Override
    public void showResultWithoutBody(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void showCover(String url) {
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.book2)
                .centerCrop()
                .error(R.mipmap.book1)
                .into(mImageView);
    }

    @Override
    public void setTitle(String title) {
        mToolbarLayout.setTitle(title);
    }
}
