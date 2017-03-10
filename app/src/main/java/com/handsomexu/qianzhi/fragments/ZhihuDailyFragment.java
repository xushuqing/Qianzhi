package com.handsomexu.qianzhi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomexu.qianzhi.interfaces.ZhihuDailyContract;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View{

    public ZhihuDailyFragment() {
    }

    public static ZhihuDailyFragment newInstance(){
        return  new ZhihuDailyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return null;
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presentr) {

    }

    @Override
    public void initView(View viewview) {

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
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {

    }

    @Override
    public void showPickDialog() {

    }
}
