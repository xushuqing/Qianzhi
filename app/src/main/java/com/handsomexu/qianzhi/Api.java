package com.handsomexu.qianzhi;

/**
 * Created by HandsomeXu on 2017/3/10.
 */

public class Api {
    //知乎日报
    //在最新信息中获取到的id,拼接到这个NEWS后，可获得对应的Json数据
    public static final String ZHIHU_NEWS = "http://news-at.zhihu.com/api/4/news/";
    //历史信息
    //知乎日报生日为2013-05-19，那么只能获取到这一天及以后的信息
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";

    //果壳文章列表
    public static final String GUOKR_ARTICLES = "http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=25&ad=1";
    //果壳文章具体信息
    public static final String GUOKR_ARTICLE_LINK_V1 = "http://jingxuan.guokr.com/pick/";

    //豆瓣一刻
    //根据日期查询信息列表，豆瓣一刻生日2014-05-12
    public static final String DOUBAN_MOMENT = "https://moment.douban.com/api/stream/date/";
    // 获取文章具体内容
    public static final String DOUBAN_ARTICLE_DETAIL = "https://moment.douban.com/api/post/";


}
