package com.ltqh.qh.entity;

public class TipPersonEntity {


    /**
     * code : 1
     * msg : 修改成功！
     * data : {"user_nickname":"用户810412","avatar":"20190916/caccdd0c3f08509ff2addfaf3b06e2db.jpg","signature":"","user_url":"","sex":0,"birthday":0}
     */

    private int code;
    private String msg;
    private DataBean data;

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
        /**
         * user_nickname : 用户810412
         * avatar : 20190916/caccdd0c3f08509ff2addfaf3b06e2db.jpg
         * signature :
         * user_url :
         * sex : 0
         * birthday : 0
         */

        private String user_nickname;
        private String avatar;
        private String signature;
        private String user_url;
        private int sex;
        private int birthday;

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

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getUser_url() {
            return user_url;
        }

        public void setUser_url(String user_url) {
            this.user_url = user_url;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }
    }
}
