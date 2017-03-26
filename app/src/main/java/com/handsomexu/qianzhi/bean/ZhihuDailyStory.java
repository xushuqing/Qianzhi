package com.handsomexu.qianzhi.bean;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/17.
 * <p>
 * Json数据：
 * {
 * "body": "HTML格式内容",
 * "image_source": "《那些年，我们一起追的女孩》",
 * "title": "瞎扯 · 如何正确地吐槽",
 * "image": "http://pic1.zhimg.com/13ee386166c53553ea6997d821609e0c.jpg",
 * "share_url": "http://daily.zhihu.com/story/9195072",
 * "js": [],
 * "ga_prefix": "020706",
 * "section": {
 * "thumbnail": "http://pic2.zhimg.com/1dc9cf1556c7b0b1527c18476698c5cd.jpg",
 * "id": 2,
 * "name": "瞎扯"
 * },
 * "images": [
 * "http://pic2.zhimg.com/1dc9cf1556c7b0b1527c18476698c5cd.jpg"
 * ],
 * "type": 0,
 * "id": 9195072,
 * "css": [
 * "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"
 * ]
 * }
 */

public class ZhihuDailyStory {
    String body;
    String image_source;
    String title;
    String image;
    String share_url;
    ArrayList<String> js;
    String ga_prefix;
    Section section;
    ArrayList<String> images;
    int type;
    int id;
    ArrayList<String> css;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ArrayList<String> getJs() {
        return js;
    }

    public void setJs(ArrayList<String> js) {
        this.js = js;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }

    class Section {
        String thumbnail;
        int id;
        String name;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
