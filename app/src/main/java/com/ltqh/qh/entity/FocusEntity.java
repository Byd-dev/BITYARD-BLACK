package com.ltqh.qh.entity;

import java.util.List;

public class FocusEntity {


    /**
     * code : 1
     * msg : success
     * data : [{"user_nickname":"是否还得考虑是否会开始","avatar":"","id":17,"mobile":"13576898013"},{"user_nickname":"Orico","avatar":"http://data.yingju8.com/upload/20181101/1f8bd7d84897480ddbd688ce245054b6.png","id":18,"mobile":"18609263478"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "FocusEntity{" +
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_nickname : 是否还得考虑是否会开始
         * avatar :
         * id : 17
         * mobile : 13576898013
         */

        private String user_nickname;
        private String avatar;
        private int id;
        private String mobile;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_nickname='" + user_nickname + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", id=" + id +
                    ", mobile='" + mobile + '\'' +
                    '}';
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
