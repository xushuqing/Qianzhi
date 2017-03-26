package com.handsomexu.qianzhi.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.handsomexu.qianzhi.ClassImpl.StringModelImpl;
import com.handsomexu.qianzhi.R;
import com.handsomexu.qianzhi.api.Api;
import com.handsomexu.qianzhi.bean.BeanType;
import com.handsomexu.qianzhi.bean.ZhihuDailyStory;
import com.handsomexu.qianzhi.interfaces.DetailContract;
import com.handsomexu.qianzhi.interfaces.OnStringListener;
import com.handsomexu.qianzhi.util.NetworkState;

/**
 * Created by HandsomeXu on 2017/3/17.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private Context context;
    private DetailContract.View view;
    private StringModelImpl model;
    private ZhihuDailyStory zhihuDailyStory;
    private Intent intent;
    private Gson gson;

    private BeanType beanType;
    private long id;
    private String title;
    private String coverUrl;


    public DetailPresenter(Context context,DetailContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        gson = new Gson();
    }

    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {
        try{
            Intent intent =  new Intent(Intent.ACTION_VIEW);
            switch (beanType){
                case ZHIHU:
                    intent.setData(Uri.parse(zhihuDailyStory.getShare_url()));
                case GUOKR:
                    break;
                case DOUBAN:
                    break;
            }
            context.startActivity(intent);
        }catch (android.content.ActivityNotFoundException e){
            view.showBrowserNotFondError();
        }

    }

    @Override
    public void shareAsText() {
    try{
        Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
        String shareText = "" + title + " ";

        switch (beanType) {
            case ZHIHU:
                shareText += zhihuDailyStory.getShare_url();
                break;
            case GUOKR:
                break;
            case DOUBAN:
        }

        shareText = shareText + "\t\t\t" + context.getString(R.string.share_extra);

        shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
        context.startActivity(Intent.createChooser(shareIntent,context.getString(R.string.share_to)));
    } catch (android.content.ActivityNotFoundException ex){
        view.showLoadingError();
    }
    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addOrRemoveFromBookmarks() {

    }

    @Override
    public boolean queryIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {

        view.showLoading();
        view.setTitle(title);
        view.showCover(coverUrl);

        if(NetworkState.networkConnected(context)){
            model.load(Api.ZHIHU_NEWS + Long.toString(id), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                   if(zhihuDailyStory.getBody() == null){
                        view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                   }else {
                       view.showResult(covertZhihuContent(zhihuDailyStory.getBody()));
                   }
                   view.stopLoading();
                }

                @Override
                public void onFailure(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        }
    }

    private String covertZhihuContent(String preResult) {
        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }


    public BeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanType beanType) {
        this.beanType = beanType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
