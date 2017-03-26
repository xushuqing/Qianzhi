package com.handsomexu.qianzhi.bean;

import java.util.ArrayList;

/**
 * Created by HandsomeXu on 2017/3/10.
 * {
 * "date": "20170310",
 * "stories": [
 * {
 * "images": [
 * "http://pic3.zhimg.com/2fe7996d412382fff6cf70ecdc2239b6.jpg"
 * ],
 * "type": 0,
 * "id": 9262570,
 * "ga_prefix": "031022",
 * "title": "小事 · 菜贱伤农，菜贵伤民"
 * }
 * ...
 */

public class ZhihuDailyNews {
    String date;
    ArrayList<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public class Story {
        ArrayList<String> images;
        int type;
        int id;
        String title;
        String ga_prefix;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }
    }

}
