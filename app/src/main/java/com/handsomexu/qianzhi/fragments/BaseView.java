package com.handsomexu.qianzhi.fragments;

import android.view.View;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public interface BaseView<T> {


    //为View设置Presenter
    void setPresenter(T presenter);

    //初始化View
    void initView(View view);

}
