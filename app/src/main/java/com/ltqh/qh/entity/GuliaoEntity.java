package com.ltqh.qh.entity;

import java.util.List;

public class GuliaoEntity {


    /**
     * code : 0
     * msg : 获取数据成功
     * data : {"total":18,"per_page":10,"current_page":1,"last_page":2,"data":[{"id":50,"user_id":18,"post_status":1,"comment_status":1,"post_hits":19,"post_like":1,"comment_count":3,"create_time":1543457096,"update_time":0,"published_time":"11-29 10:04","delete_time":0,"post_title":"广电网络早市，很不错的样子呢！","post_excerpt":"","post_content":"广电网络早市，很不错的样子呢！","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181129/b101c1edfb05168fc468e8471bf06ced.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":49,"user_id":18,"post_status":1,"comment_status":1,"post_hits":161,"post_like":1,"comment_count":6,"create_time":1543285674,"update_time":0,"published_time":"11-27 10:27","delete_time":0,"post_title":"上证指数","post_excerpt":"","post_content":"上证指数","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181127/7ed5c5ef2466d129b8547a0ceb7f0c90.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":48,"user_id":18,"post_status":1,"comment_status":1,"post_hits":5,"post_like":0,"comment_count":0,"create_time":1543283926,"update_time":0,"published_time":"11-27 09:58","delete_time":0,"post_title":"早市您 好啊","post_excerpt":"","post_content":"早市您 好啊","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181126/c5a761ce11bc747661c63e5971b2fcfe.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":47,"user_id":18,"post_status":1,"comment_status":1,"post_hits":2,"post_like":0,"comment_count":0,"create_time":1543283895,"update_time":0,"published_time":"11-27 09:58","delete_time":0,"post_title":"早市您 好啊","post_excerpt":"","post_content":"早市您 好啊","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":46,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543234874,"update_time":0,"published_time":"11-26 20:21","delete_time":0,"post_title":"股票大牛来点资料","post_excerpt":"","post_content":"股票大牛来点资料","post_content_filtered":null,"more":"http://img0.imgtn.bdimg.com/it/u=2181161660,2650271690&amp;fm=26&amp;gp=0.jpg","share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":45,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543201179,"update_time":0,"published_time":"11-26 10:59","delete_time":0,"post_title":"股票大牛来点资料","post_excerpt":"","post_content":"股票大牛来点资料","post_content_filtered":null,"more":"http://img0.imgtn.bdimg.com/it/u=2181161660,2650271690&amp;fm=26&amp;gp=0.jpg","share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":44,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543201103,"update_time":0,"published_time":"11-26 10:58","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":43,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543200732,"update_time":0,"published_time":"11-26 10:52","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":42,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1542968634,"update_time":0,"published_time":"11-23 18:23","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":41,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1542880168,"update_time":0,"published_time":"11-22 17:49","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null}]}
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
        /**
         * total : 18
         * per_page : 10
         * current_page : 1
         * last_page : 2
         * data : [{"id":50,"user_id":18,"post_status":1,"comment_status":1,"post_hits":19,"post_like":1,"comment_count":3,"create_time":1543457096,"update_time":0,"published_time":"11-29 10:04","delete_time":0,"post_title":"广电网络早市，很不错的样子呢！","post_excerpt":"","post_content":"广电网络早市，很不错的样子呢！","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181129/b101c1edfb05168fc468e8471bf06ced.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":49,"user_id":18,"post_status":1,"comment_status":1,"post_hits":161,"post_like":1,"comment_count":6,"create_time":1543285674,"update_time":0,"published_time":"11-27 10:27","delete_time":0,"post_title":"上证指数","post_excerpt":"","post_content":"上证指数","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181127/7ed5c5ef2466d129b8547a0ceb7f0c90.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":48,"user_id":18,"post_status":1,"comment_status":1,"post_hits":5,"post_like":0,"comment_count":0,"create_time":1543283926,"update_time":0,"published_time":"11-27 09:58","delete_time":0,"post_title":"早市您 好啊","post_excerpt":"","post_content":"早市您 好啊","post_content_filtered":null,"more":"https://data.yingju8.com/upload/20181126/c5a761ce11bc747661c63e5971b2fcfe.jpg","share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":47,"user_id":18,"post_status":1,"comment_status":1,"post_hits":2,"post_like":0,"comment_count":0,"create_time":1543283895,"update_time":0,"published_time":"11-27 09:58","delete_time":0,"post_title":"早市您 好啊","post_excerpt":"","post_content":"早市您 好啊","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":{"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}},{"id":46,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543234874,"update_time":0,"published_time":"11-26 20:21","delete_time":0,"post_title":"股票大牛来点资料","post_excerpt":"","post_content":"股票大牛来点资料","post_content_filtered":null,"more":"http://img0.imgtn.bdimg.com/it/u=2181161660,2650271690&amp;fm=26&amp;gp=0.jpg","share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":45,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543201179,"update_time":0,"published_time":"11-26 10:59","delete_time":0,"post_title":"股票大牛来点资料","post_excerpt":"","post_content":"股票大牛来点资料","post_content_filtered":null,"more":"http://img0.imgtn.bdimg.com/it/u=2181161660,2650271690&amp;fm=26&amp;gp=0.jpg","share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":44,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543201103,"update_time":0,"published_time":"11-26 10:58","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":1,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":43,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1543200732,"update_time":0,"published_time":"11-26 10:52","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":42,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1542968634,"update_time":0,"published_time":"11-23 18:23","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null},{"id":41,"user_id":15,"post_status":1,"comment_status":1,"post_hits":0,"post_like":0,"comment_count":0,"create_time":1542880168,"update_time":0,"published_time":"11-22 17:49","delete_time":0,"post_title":"test","post_excerpt":"","post_content":"abcd","post_content_filtered":null,"more":null,"share_id":22,"last_time":null,"favorite_count":0,"report_count":0,"report_status":0,"user":null}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        @Override
        public String toString() {
            return "DataBeanX{" +
                    "total=" + total +
                    ", per_page=" + per_page +
                    ", current_page=" + current_page +
                    ", last_page=" + last_page +
                    ", data=" + data +
                    '}';
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
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
            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", user_id=" + user_id +
                        ", post_status=" + post_status +
                        ", comment_status=" + comment_status +
                        ", post_hits=" + post_hits +
                        ", post_like=" + post_like +
                        ", comment_count=" + comment_count +
                        ", create_time=" + create_time +
                        ", update_time=" + update_time +
                        ", published_time='" + published_time + '\'' +
                        ", delete_time=" + delete_time +
                        ", post_title='" + post_title + '\'' +
                        ", post_excerpt='" + post_excerpt + '\'' +
                        ", post_content='" + post_content + '\'' +
                        ", post_content_filtered=" + post_content_filtered +
                        ", more='" + more + '\'' +
                        ", share_id=" + share_id +
                        ", last_time=" + last_time +
                        ", favorite_count=" + favorite_count +
                        ", report_count=" + report_count +
                        ", report_status=" + report_status +
                        ", user=" + user +
                        '}';
            }

            /**
             * id : 50
             * user_id : 18
             * post_status : 1
             * comment_status : 1
             * post_hits : 19
             * post_like : 1
             * comment_count : 3
             * create_time : 1543457096
             * update_time : 0
             * published_time : 11-29 10:04
             * delete_time : 0
             * post_title : 广电网络早市，很不错的样子呢！
             * post_excerpt :
             * post_content : 广电网络早市，很不错的样子呢！
             * post_content_filtered : null
             * more : https://data.yingju8.com/upload/20181129/b101c1edfb05168fc468e8471bf06ced.jpg
             * share_id : 22
             * last_time : null
             * favorite_count : 0
             * report_count : 0
             * report_status : 0
             * user : {"id":18,"user_nickname":"小小S","avatar":"http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg"}
             */


            private int id;
            private int user_id;
            private int post_status;
            private int comment_status;
            private int post_hits;
            private int post_like;
            private int comment_count;
            private int create_time;
            private int update_time;
            private String published_time;
            private int delete_time;
            private String post_title;
            private String post_excerpt;
            private String post_content;
            private Object post_content_filtered;
            private String more;
            private int share_id;
            private Object last_time;
            private int favorite_count;
            private int report_count;
            private int report_status;
            private UserBean user;

            private boolean focus;


            public boolean isFocus() {
                return false;
            }

            public void setFocus(boolean focus) {
                this.focus = focus;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
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

            public String getPost_excerpt() {
                return post_excerpt;
            }

            public void setPost_excerpt(String post_excerpt) {
                this.post_excerpt = post_excerpt;
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

            public String getMore() {
                return more;
            }

            public void setMore(String more) {
                this.more = more;
            }

            public int getShare_id() {
                return share_id;
            }

            public void setShare_id(int share_id) {
                this.share_id = share_id;
            }

            public Object getLast_time() {
                return last_time;
            }

            public void setLast_time(Object last_time) {
                this.last_time = last_time;
            }

            public int getFavorite_count() {
                return favorite_count;
            }

            public void setFavorite_count(int favorite_count) {
                this.favorite_count = favorite_count;
            }

            public int getReport_count() {
                return report_count;
            }

            public void setReport_count(int report_count) {
                this.report_count = report_count;
            }

            public int getReport_status() {
                return report_status;
            }

            public void setReport_status(int report_status) {
                this.report_status = report_status;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * id : 18
                 * user_nickname : 小小S
                 * avatar : http://data.yingju8.com/upload/20181204/b7538e54e3033f5c8f47aee2f318b31f.jpg
                 */

                private int id;
                private String user_nickname;
                private String avatar;

                @Override
                public String toString() {
                    return "UserBean{" +
                            "id=" + id +
                            ", user_nickname='" + user_nickname + '\'' +
                            ", avatar='" + avatar + '\'' +
                            '}';
                }

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
            }
        }
    }
}
