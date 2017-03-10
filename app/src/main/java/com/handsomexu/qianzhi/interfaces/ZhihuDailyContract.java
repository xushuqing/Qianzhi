package com.handsomexu.qianzhi.interfaces;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/10.
 *
 * 知乎日报契约类，用于管理对应的View和Presenter
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {

        //显示加载或其他类型的错误
        void showError();

        //显示正在加载
        void showLoading();

        //停止显示正在加载
        void stopLoading();

        //成功获取数据后，在界面显示
        void showResults(ArrayList<ZhihuDailyNews.Question> list);
        //显示日期选择器
        void showPickDialog();
    }


    interface Presenter extends BasePresenter{
        //请求数据
        void loadPosts(long date,boolean clearing);
        //刷新数据
        void refresh();
        //加载更多
        void loadMore();
        //显示详情
        void startReading(int position);
        //随便看看
        void feelLucky();
    }
}
