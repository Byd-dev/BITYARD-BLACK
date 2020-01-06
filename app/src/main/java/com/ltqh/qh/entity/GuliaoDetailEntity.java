package com.ltqh.qh.entity;

import java.util.List;

public class GuliaoDetailEntity {


    /**
     * code : 1
     * msg : 请求成功
     * data : {"id":3716,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":4,"post_like":0,"comment_count":0,"create_time":"2019-12-20 14:18:06","update_time":"2019-12-20 14:18:06","published_time":"2019-12-20 14:18:06","delete_time":0,"post_title":"BTC回暖","post_keywords":"BTC回暖","post_excerpt":"BTC回暖","post_source":"用户851419","post_content":"这一波你们怎么看 ","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[]}
     */

    private int code;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "GuliaoDetailEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", parent_id=" + parent_id +
                    ", post_type=" + post_type +
                    ", post_format=" + post_format +
                    ", user_id=" + user_id +
                    ", post_status=" + post_status +
                    ", comment_status=" + comment_status +
                    ", is_top=" + is_top +
                    ", recommended=" + recommended +
                    ", post_hits=" + post_hits +
                    ", post_like=" + post_like +
                    ", comment_count=" + comment_count +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", published_time='" + published_time + '\'' +
                    ", delete_time=" + delete_time +
                    ", post_title='" + post_title + '\'' +
                    ", post_keywords='" + post_keywords + '\'' +
                    ", post_excerpt='" + post_excerpt + '\'' +
                    ", post_source='" + post_source + '\'' +
                    ", post_content='" + post_content + '\'' +
                    ", post_content_filtered=" + post_content_filtered +
                    ", article_flag='" + article_flag + '\'' +
                    ", aid='" + aid + '\'' +
                    ", report_status=" + report_status +
                    ", is_topic=" + is_topic +
                    ", apply_name='" + apply_name + '\'' +
                    ", user=" + user +
                    ", more=" + more +
                    ", expand=" + expand +
                    ", video_url=" + video_url +
                    ", comment=" + comment +
                    '}';
        }

        /**
         * id : 3716
         * parent_id : 0
         * post_type : 1
         * post_format : 1
         * user_id : 4621
         * post_status : 1
         * comment_status : 1
         * is_top : 0
         * recommended : 0
         * post_hits : 4
         * post_like : 0
         * comment_count : 0
         * create_time : 2019-12-20 14:18:06
         * update_time : 2019-12-20 14:18:06
         * published_time : 2019-12-20 14:18:06
         * delete_time : 0
         * post_title : BTC回暖
         * post_keywords : BTC回暖
         * post_excerpt : BTC回暖
         * post_source : 用户851419
         * post_content : 这一波你们怎么看
         * post_content_filtered : null
         * more : []
         * article_flag :
         * aid : 0
         * report_status : 0
         * is_topic : 0
         * apply_name : com.fincoin.cyptonline
         * expand : []
         * video_url : []
         * user : {"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"}
         * comment : []
         */



        private int id;
        private int parent_id;
        private int post_type;
        private int post_format;
        private int user_id;
        private int post_status;
        private int comment_status;
        private int is_top;
        private int recommended;
        private int post_hits;
        private int post_like;
        private int comment_count;
        private String create_time;
        private String update_time;
        private String published_time;
        private int delete_time;
        private String post_title;
        private String post_keywords;
        private String post_excerpt;
        private String post_source;
        private String post_content;
        private Object post_content_filtered;
        private String article_flag;
        private String aid;
        private int report_status;
        private int is_topic;
        private String apply_name;
        private UserBean user;
        private List<?> more;
        private List<?> expand;
        private List<?> video_url;
        private List<?> comment;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getPost_type() {
            return post_type;
        }

        public void setPost_type(int post_type) {
            this.post_type = post_type;
        }

        public int getPost_format() {
            return post_format;
        }

        public void setPost_format(int post_format) {
            this.post_format = post_format;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getPost_status() {
            return post_status;
        }

        public void setPost_status(int post_status) {
            this.post_status = post_status;
        }

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public int getRecommended() {
            return recommended;
        }

        public void setRecommended(int recommended) {
            this.recommended = recommended;
        }

        public int getPost_hits() {
            return post_hits;
        }

        public void setPost_hits(int post_hits) {
            this.post_hits = post_hits;
        }

        public int getPost_like() {
            return post_like;
        }

        public void setPost_like(int post_like) {
            this.post_like = post_like;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPublished_time() {
            return published_time;
        }

        public void setPublished_time(String published_time) {
            this.published_time = published_time;
        }

        public int getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(int delete_time) {
            this.delete_time = delete_time;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_keywords() {
            return post_keywords;
        }

        public void setPost_keywords(String post_keywords) {
            this.post_keywords = post_keywords;
        }

        public String getPost_excerpt() {
            return post_excerpt;
        }

        public void setPost_excerpt(String post_excerpt) {
            this.post_excerpt = post_excerpt;
        }

        public String getPost_source() {
            return post_source;
        }

        public void setPost_source(String post_source) {
            this.post_source = post_source;
        }

        public String getPost_content() {
            return post_content;
        }

        public void setPost_content(String post_content) {
            this.post_content = post_content;
        }

        public Object getPost_content_filtered() {
            return post_content_filtered;
        }

        public void setPost_content_filtered(Object post_content_filtered) {
            this.post_content_filtered = post_content_filtered;
        }

        public String getArticle_flag() {
            return article_flag;
        }

        public void setArticle_flag(String article_flag) {
            this.article_flag = article_flag;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public int getReport_status() {
            return report_status;
        }

        public void setReport_status(int report_status) {
            this.report_status = report_status;
        }

        public int getIs_topic() {
            return is_topic;
        }

        public void setIs_topic(int is_topic) {
            this.is_topic = is_topic;
        }

        public String getApply_name() {
            return apply_name;
        }

        public void setApply_name(String apply_name) {
            this.apply_name = apply_name;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<?> getMore() {
            return more;
        }

        public void setMore(List<?> more) {
            this.more = more;
        }

        public List<?> getExpand() {
            return expand;
        }

        public void setExpand(List<?> expand) {
            this.expand = expand;
        }

        public List<?> getVideo_url() {
            return video_url;
        }

        public void setVideo_url(List<?> video_url) {
            this.video_url = video_url;
        }

        public List<?> getComment() {
            return comment;
        }

        public void setComment(List<?> comment) {
            this.comment = comment;
        }

        public static class UserBean {
            /**
             * id : 4621
             * user_nickname : 小仙女
             * avatar : https://d.wanjinig.cn/upload/avatar/logo.png
             * mobile : 13555555555
             */

            private int id;
            private String user_nickname;
            private String avatar;
            private String mobile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
