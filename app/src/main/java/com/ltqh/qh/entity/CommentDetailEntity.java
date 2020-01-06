package com.ltqh.qh.entity;

import java.util.List;

public class CommentDetailEntity {


    /**
     * code : 1
     * msg : 请求成功
     * data : {"total":2,"per_page":"10","current_page":1,"last_page":1,"data":[{"id":539,"user_id":4678,"object_id":3716,"like_count":0,"create_time":"2019-12-20 15:09:57","full_name":"用户866065","content":"呵呵呵呵","more":null,"user":{"id":4678,"user_nickname":"用户866065","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13666666666"}},{"id":538,"user_id":4678,"object_id":3716,"like_count":0,"create_time":"2019-12-20 15:00:14","full_name":"用户866065","content":"还好还好哈","more":null,"user":{"id":4678,"user_nickname":"用户866065","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13666666666"}}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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
         * total : 2
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":539,"user_id":4678,"object_id":3716,"like_count":0,"create_time":"2019-12-20 15:09:57","full_name":"用户866065","content":"呵呵呵呵","more":null,"user":{"id":4678,"user_nickname":"用户866065","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13666666666"}},{"id":538,"user_id":4678,"object_id":3716,"like_count":0,"create_time":"2019-12-20 15:00:14","full_name":"用户866065","content":"还好还好哈","more":null,"user":{"id":4678,"user_nickname":"用户866065","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13666666666"}}]
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
             * id : 539
             * user_id : 4678
             * object_id : 3716
             * like_count : 0
             * create_time : 2019-12-20 15:09:57
             * full_name : 用户866065
             * content : 呵呵呵呵
             * more : null
             * user : {"id":4678,"user_nickname":"用户866065","avatar":"https://d.wanjinig.cn/upload/avatar/logo.png","mobile":"13666666666"}
             */

            private int id;
            private int user_id;
            private int object_id;
            private int like_count;
            private String create_time;
            private String full_name;
            private String content;
            private Object more;
            private UserBean user;

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

            public int getObject_id() {
                return object_id;
            }

            public void setObject_id(int object_id) {
                this.object_id = object_id;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getMore() {
                return more;
            }

            public void setMore(Object more) {
                this.more = more;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * id : 4678
                 * user_nickname : 用户866065
                 * avatar : https://d.wanjinig.cn/upload/avatar/logo.png
                 * mobile : 13666666666
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
