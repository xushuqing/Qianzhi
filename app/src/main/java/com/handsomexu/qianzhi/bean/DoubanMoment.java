package com.handsomexu.qianzhi.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HandsomeXu on 2017/3/21.
 * {
 * "count": 0,
 * "posts": [
 * {
 * "display_style": 10001,
 * "is_editor_choice": false,
 * "published_time": "2017-03-25 14:00:00",
 * "original_url": "https://www.douban.com/note/609409928/",
 * "url": "https://moment.douban.com/post/165326/?douban_rec=1",
 * "short_url": "https://dou.bz/1C15m3",
 * "is_liked": false,
 * "author": {
 * "is_followed": false,
 * "uid": "charlie_king",
 * "url": "https://www.douban.com/people/charlie_king/",
 * "avatar": "https://img3.doubanio.com/icon/u1347949-20.jpg",
 * "name": "青竹子",
 * "is_special_user": false,
 * "n_posts": 0,
 * "alt": "不是学问家",
 * "large_avatar": "https://img3.doubanio.com/icon/up1347949-20.jpg",
 * "id": "1347949",
 * "is_auth_author": false
 * },
 * "column": "",
 * "app_css": 7,
 * "abstract": "这是我首次在梦中意识到以前经历过的梦境，首次在梦中将曾经的梦境当成了真实的记忆。做梦，在梦境中进入虚拟现实，观看一个秦代文物展览。看完展览走出虚拟现实，回到梦境。然后再从梦中醒来，回到现实世界。",
 * "date": "2017-03-25",
 * "like_count": 5,
 * "comments_count": 0,
 * "thumbs": [],
 * "created_time": "2017-03-06 14:35:39",
 * "title": "长文｜旧梦集",
 * "share_pic_url": "https://moment.douban.com/share_pic/post/165326.jpg",
 * "type": "1001",
 * "id": 165326
 * },
 * {
 * "display_style": 10001,
 * .
 * .
 * .
 * .
 * .
 * ],
 * "offset": 7200,
 * "date": "2017-03-25",
 * "total": 11
 * }
 */

public class DoubanMoment {


    public class posts {

        private String published_time;
        private String created_time;
        private String title;
        private String share_pic_url;
        private int id;

        public String getPublished_time() {
            return published_time;
        }

        public void setPublished_time(String published_time) {
            this.published_time = published_time;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShare_pic_url() {
            return share_pic_url;
        }

        public void setShare_pic_url(String share_pic_url) {
            this.share_pic_url = share_pic_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

}
