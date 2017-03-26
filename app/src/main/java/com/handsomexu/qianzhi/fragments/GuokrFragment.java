package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.adapter.GuokrAdapter;
import com.handsomexu.qianzhi.bean.GuokrNews;
import com.handsomexu.qianzhi.interfaces.GuokrContract;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/11.
 */

public class GuokrFragment extends Fragment implements GuokrContract.View {
    public static GuokrContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private GuokrAdapter mAdapter;

    private GuokrFragment() {
    }

    public static GuokrFragment newInstance() {
        GuokrFragment fragment = new GuokrFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        presenter.start();
        return view;
    }

    @Override
    public void setPresenter(GuokrContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //@param hasFixedSize true if mAdapter changes cannot affect the size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

    }

    @Override
    public void showError() {
        mRefresh.setRefreshing(false);
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
    public void showResults(ArrayList<GuokrNews.Result> list) {
        if (mAdapter == null) {
            mAdapter = new GuokrAdapter(getContext(), list);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    presenter.startReading(position);
                }
            });
        }
        mAdapter.notifyDataSetChanged();
    }
}
