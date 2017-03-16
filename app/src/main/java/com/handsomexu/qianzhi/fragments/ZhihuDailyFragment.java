package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.adapter.ZhihuDailyNewsAdapter;
import com.handsomexu.qianzhi.bean.ZhihuDailyNews;
import com.handsomexu.qianzhi.interfaces.OnRecyclerViewOnClickListener;
import com.handsomexu.qianzhi.interfaces.ZhihuDailyContract;
import com.handsomexu.qianzhi.presenter.ZhihuDailyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{

    private RecyclerView recyclerView;
    private ZhihuDailyNewsAdapter adapter;
    private ZhihuDailyContract.Presenter presenter;
    private ZhihuDailyFragment mZhihuDailyFragment;
    private ZhihuDailyFragment() {
    }

    public static ZhihuDailyFragment newInstance(){
        return  new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZhihuDailyFragment = ZhihuDailyFragment.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter.loadPosts(System.currentTimeMillis(),false);
        View view = inflater.inflate(R.layout.fragment_zhihu,container,false);
        initView(view);
        return view;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView(View view) {
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(manager);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(final ArrayList<ZhihuDailyNews.Story> list) {
        if(adapter == null){
            adapter = new ZhihuDailyNewsAdapter(getContext(),list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);

        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showPickDialog() {

    }
}
