package com.ltqh.qh.entity;

import java.util.List;

public class GuliaoEntity {


    /**
     * code : 1
     * msg : 请求成功
     * data : {"total":5,"per_page":"10","current_page":1,"last_page":1,"data":[{"id":3715,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-20 13:42:00","update_time":"2019-12-20 13:42:00","published_time":"2019-12-20 13:42:00","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3714,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-20 13:41:55","update_time":"2019-12-20 13:41:55","published_time":"2019-12-20 13:41:55","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3712,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 19:16:57","update_time":"2019-12-19 19:16:57","published_time":"2019-12-19 19:16:57","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3711,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 19:05:14","update_time":"2019-12-19 19:05:14","published_time":"2019-12-19 19:05:14","delete_time":0,"post_title":"古古怪怪","post_keywords":"古古怪怪","post_excerpt":"古古怪怪","post_source":"用户851419","post_content":"哈哈哈哈嗝姑姑姑父","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3710,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 18:42:04","update_time":"2019-12-19 18:42:04","published_time":"2019-12-19 18:42:04","delete_time":0,"post_title":"usdt","post_keywords":"usdt","post_excerpt":"usdt","post_source":"用户851419","post_content":"买点哈哈哈哈\n","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

    @Override
    public String toString() {
        return "GuliaoEntity{" +
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        @Override
        public String toString() {
            return "DataBeanX{" +
                    "total=" + total +
                    ", per_page='" + per_page + '\'' +
                    ", current_page=" + current_page +
                    ", last_page=" + last_page +
                    ", data=" + data +
                    '}';
        }

        /**
         * total : 5
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":3715,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-20 13:42:00","update_time":"2019-12-20 13:42:00","published_time":"2019-12-20 13:42:00","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3714,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-20 13:41:55","update_time":"2019-12-20 13:41:55","published_time":"2019-12-20 13:41:55","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3712,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 19:16:57","update_time":"2019-12-19 19:16:57","published_time":"2019-12-19 19:16:57","delete_time":0,"post_title":"哈哈哈哈哈","post_keywords":"哈哈哈哈","post_excerpt":"哈哈哈","post_source":"小仙女","post_content":"工信部部长苗圩：中国将全面放开一般制造业","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3711,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 19:05:14","update_time":"2019-12-19 19:05:14","published_time":"2019-12-19 19:05:14","delete_time":0,"post_title":"古古怪怪","post_keywords":"古古怪怪","post_excerpt":"古古怪怪","post_source":"用户851419","post_content":"哈哈哈哈嗝姑姑姑父","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0},{"id":3710,"parent_id":0,"post_type":1,"post_format":1,"user_id":4621,"post_status":1,"comment_status":1,"is_top":0,"recommended":0,"post_hits":0,"post_like":0,"comment_count":0,"create_time":"2019-12-19 18:42:04","update_time":"2019-12-19 18:42:04","published_time":"2019-12-19 18:42:04","delete_time":0,"post_title":"usdt","post_keywords":"usdt","post_excerpt":"usdt","post_source":"用户851419","post_content":"买点哈哈哈哈\n","post_content_filtered":null,"more":[],"article_flag":"","aid":"0","report_status":0,"is_topic":0,"apply_name":"com.fincoin.cyptonline","expand":[],"video_url":[],"user":{"id":4621,"user_nickname":"小仙女","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13555555555"},"comment":[],"is_follow":0}]
         */



        private int total;
        private String per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 3715
             * parent_id : 0
             * post_type : 1
             * post_format : 1
             * user_id : 4621
             * post_status : 1
             * comment_status : 1
             * is_top : 0
             * recommended : 0
             * post_hits : 0
             * post_like : 0
             * comment_count : 0
             * create_time : 2019-12-20 13:42:00
             * update_time : 2019-12-20 13:42:00
             * published_time : 2019-12-20 13:42:00
             * delete_time : 0
             * post_title : 哈哈哈哈哈
             * post_keywords : 哈哈哈哈
             * post_excerpt : 哈哈哈
             * post_source : 小仙女
             * post_content : 工信部部长苗圩：中国将全面放开一般制造业
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
             * is_follow : 0
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
            private int is_follow;
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

            public int getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(int is_follow) {
                this.is_follow = is_follow;
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
}
