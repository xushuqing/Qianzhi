package com.handsomexu.qianzhi.bean;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/20.
 */

public class GuokrNews {
    String now;
    boolean ok;
    ArrayList<Result> result;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {
        String link_v2_sync_img;
        String source_name;
        String[] images;
        int id;
        String category;
        String style;
        String title;
        String headline_img_tb;

        public String getHeadline_img_tb() {
            return headline_img_tb;
        }

        public void setHeadline_img_tb(String headline_img_tb) {
            this.headline_img_tb = headline_img_tb;
        }

        public String getLink_v2_sync_img() {
            return link_v2_sync_img;
        }

        public void setLink_v2_sync_img(String link_v2_sync_img) {
            this.link_v2_sync_img = link_v2_sync_img;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String[] getImages() {
            return images;
        }

        public void setImages(String[] images) {
            this.images = images;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
