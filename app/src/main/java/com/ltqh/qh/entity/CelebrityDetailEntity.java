package com.ltqh.qh.entity;

public class CelebrityDetailEntity {


    /**
     * code : 1
     * msg : 获取数据成功
     * data : {"jianjie":"朱瑞清先生，Tripio联合创始人 & CTO，拥有美国弗吉尼亚大学计算机硕士学位、浙江大学计算机学士学位。曾任 Expedia 旗下艺龙旅行网副总裁，Amazon 技术总监，供应链和大数据系统专家，在中美有16年软件研发和产品管理经验。在美国生活了15年，主要在亚马逊和微软工作，2014年回国创业。创业的第一个项目叫百场汇，它是一个场地短租平台，融资已有三轮。在2017年年底，开始参与孵化了第二个项目Tripio，一个基于区块链的去中心化旅行服务平台。 "}
     */

    private int code;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "CelebrityDetailEntity{" +
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
        /**
         * jianjie : 朱瑞清先生，Tripio联合创始人 & CTO，拥有美国弗吉尼亚大学计算机硕士学位、浙江大学计算机学士学位。曾任 Expedia 旗下艺龙旅行网副总裁，Amazon 技术总监，供应链和大数据系统专家，在中美有16年软件研发和产品管理经验。在美国生活了15年，主要在亚马逊和微软工作，2014年回国创业。创业的第一个项目叫百场汇，它是一个场地短租平台，融资已有三轮。在2017年年底，开始参与孵化了第二个项目Tripio，一个基于区块链的去中心化旅行服务平台。
         */

        private String jianjie;

        @Override
        public String toString() {
            return "DataBean{" +
                    "jianjie='" + jianjie + '\'' +
                    '}';
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }
    }
}
