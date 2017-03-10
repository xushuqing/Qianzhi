package com.handsomexu.qianzhi.interfaces;

import android.view.View;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public interface BaseView<T> {


    //为View设置Presenter
    void setPresenter(T presentr);

    //初始化界
    void initView(View viewview);

}
