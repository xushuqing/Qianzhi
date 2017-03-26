package com.handsomexu.qianzhi.interfaces;

import com.handsomexu.qianzhi.fragments.BaseView;
import com.handsomexu.qianzhi.presenter.BasePresenter;

/**
 * Created by HandsomeXu on 2017/3/17.
 */

public interface DetailContract {
    public interface View extends BaseView<Presenter> {
        //显示正在加载
        void showLoading();

        //停止加载
        void stopLoading();

        //显示加载错误
        void showLoadingError();

        //正确获取数据后显示内容
        void showResult(String result);

        //对于body字段的消息，直接接在url的内容
        void showResultWithoutBody(String url);

        //设置顶部大图
        void showCover(String url);

        //设置标题
        void setTitle(String title);

    }

    public interface Presenter extends BasePresenter {
        //请求数据
        void requestData();
    }
}
