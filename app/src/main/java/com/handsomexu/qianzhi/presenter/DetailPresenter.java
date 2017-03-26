package com.handsomexu.qianzhi.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handsomexu.qianzhi.Api;
import com.handsomexu.qianzhi.StringModelImpl;
import com.handsomexu.qianzhi.bean.BeanType;
import com.handsomexu.qianzhi.bean.DoubanMomentStory;
import com.handsomexu.qianzhi.bean.ZhihuDailyStory;
import com.handsomexu.qianzhi.interfaces.DetailContract;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.util.NetworkState;

/**
 * Created by HandsomeXu on 2017/3/17.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private Context mContext;
    private DetailContract.View mView;
    private StringModelImpl mModel;
    private ZhihuDailyStory mZhihuDailyStory;
    private String mGuokrStory;
    private Gson mGson;

    private BeanType mType;
    private int id;
    private String title;
    private String coverUrl;


    public DetailPresenter(Context context, DetailContract.View mView) {
        this.mContext = context;
        this.mView = mView;
        this.mView.setPresenter(this);
        mModel = new StringModelImpl(mContext);
        mGson = new Gson();
    }

    @Override
    public void start() {
        requestData();
    }

    @Override
    public void requestData() {
        if (id == 0 || mType == null) {
            mView.showLoadingError();
            return;
        }

        mView.showLoading();
        mView.setTitle(title);
        mView.showCover(coverUrl);

        if (NetworkState.networkConnected(mContext)) {
            switch (mType) {
                case ZHIHU:
                    mModel.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            mZhihuDailyStory = mGson.fromJson(result, ZhihuDailyStory.class);
                            if (mZhihuDailyStory.getBody() == null) {
                                mView.showResultWithoutBody(mZhihuDailyStory.getShare_url());
                            } else {
                                mView.showResult(covertZhihuContent(mZhihuDailyStory.getBody()));
                            }
                            mView.stopLoading();
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            mView.stopLoading();
                            mView.showLoadingError();
                        }
                    });
                    break;

                case GUOKR:
                    mModel.load(Api.GUOKR_ARTICLE_LINK_V1 + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            covertGuokrContent(result);
                            mView.showResult(mGuokrStory);
                            mView.stopLoading();
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            mView.showLoadingError();
                        }
                    });
                    break;
                case DOUBAN:
                    mModel.load(Api.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            DoubanMomentStory story = new Gson().fromJson(result, DoubanMomentStory.class);
                            String content = story.getContent();
                            mView.showResult(content);
                            mView.stopLoading();
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            mView.showLoadingError();
                        }
                    });
                    break;
            }

        } else {
            mView.showLoadingError();
        }
    }

    private String covertZhihuContent(String preResult) {
        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");
        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(preResult)
                .append("</body></html>").toString();
    }

    private void covertGuokrContent(String content) {
/**
 * <div class="down" id="down-footer">
 <img src="http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png" class="jingxuan-img">
 <p class="jingxuan-txt">
 <span class="jingxuan-title">果壳精选</span>
 <span class="jingxuan-label">早晚给你好看</span>
 </p>
 <a href="" class="app-down" id="app-down-footer">下载</a>
 </div>
 <div class="down-pc" id="down-pc">
 <img src="http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png" class="jingxuan-img">
 <p class="jingxuan-txt">
 <span class="jingxuan-title">果壳精选</span>
 <span class="jingxuan-label">早晚给你好看</span>
 </p>
 <a href="http://www.guokr.com/mobile/" class="app-down">下载</a>
 </div>
 */
        //去掉下载的div部分
        mGuokrStory = content.replace("<div class=\"down\" id=\"down-footer\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"\" class=\"app-down\" id=\"app-down-footer\">下载</a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"down-pc\" id=\"down-pc\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"http://www.guokr.com/mobile/\" class=\"app-down\">下载</a>\n" +
                "    </div>", "");


    }

    public BeanType getBeanType() {
        return mType;
    }

    public void setBeanType(BeanType mType) {
        this.mType = mType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
