package com.ltqh.qh.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BtcRankEntity {


    /**
     * code : 1
     * msg : success
     * data : {"rising":[{"rank":1,"name":"HDAC","pricing":"$0.04575046","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hdac.png"},{"rank":2,"name":"POLY","pricing":"$0.02821728","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/polymath-network"},{"rank":3,"name":"ME","pricing":"$0.05443639","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/static/home/default_img/coin_default.png"},{"rank":4,"name":"DGD","pricing":"$19.20982728","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/digixdao"},{"rank":5,"name":"BPX","pricing":"$0.00745141","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/bispextoken.png"},{"rank":6,"name":"MANA","pricing":"$0.02680396","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/decentraland"},{"rank":7,"name":"OMG","pricing":"$0.76938793","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/omisego"},{"rank":8,"name":"XB","pricing":"$0.19807621","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/xinbi-token.png"},{"rank":9,"name":"ENJ","pricing":"$0.08107314","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/enjin-coin"}],"falling":[{"rank":1,"name":"MBL","pricing":"$0.00164767","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/94778c5d56dc45eb657620c0e8b63517.png"},{"rank":2,"name":"HYC","pricing":"$0.00359243","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hycon"},{"rank":3,"name":"EKT","pricing":"$0.08982892","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/educare"},{"rank":4,"name":"ADK","pricing":"$1.1614796","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/aidos-kuneen.png"},{"rank":5,"name":"HSR","pricing":"$4.568585","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hshare"},{"rank":6,"name":"AST","pricing":"$0.02069479","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/airswap.png"},{"rank":7,"name":"MDT","pricing":"$0.0076097","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/measurable-data-token"},{"rank":8,"name":"MATIC","pricing":"$0.01758094","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/da9bc5822529a2c225e057c0d8d50f36.png"},{"rank":9,"name":"NMC","pricing":"$0.3965279","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/namecoin"}],"description":"币指如何形成：全球币指是比特财经网研究院首创的币圈指数，是以主流交易所的主流币种为计算范围，以发行量为权数的加权综合股价指数，全球币指以11月1日为基日，以当天主流币种的市价总值为基期，基点为1000点。我们通过跟踪研究2017年5月至今的数据，通过对数据的采集、比对、清洗、加工和存储, 再利用派氏加权综合价格指数，进行计算而来。"}
     */

    private int code;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "BtcRankEntity{" +
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
         * rising : [{"rank":1,"name":"HDAC","pricing":"$0.04575046","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hdac.png"},{"rank":2,"name":"POLY","pricing":"$0.02821728","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/polymath-network"},{"rank":3,"name":"ME","pricing":"$0.05443639","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/static/home/default_img/coin_default.png"},{"rank":4,"name":"DGD","pricing":"$19.20982728","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/digixdao"},{"rank":5,"name":"BPX","pricing":"$0.00745141","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/bispextoken.png"},{"rank":6,"name":"MANA","pricing":"$0.02680396","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/decentraland"},{"rank":7,"name":"OMG","pricing":"$0.76938793","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/omisego"},{"rank":8,"name":"XB","pricing":"$0.19807621","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/xinbi-token.png"},{"rank":9,"name":"ENJ","pricing":"$0.08107314","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/enjin-coin"}]
         * falling : [{"rank":1,"name":"MBL","pricing":"$0.00164767","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/94778c5d56dc45eb657620c0e8b63517.png"},{"rank":2,"name":"HYC","pricing":"$0.00359243","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hycon"},{"rank":3,"name":"EKT","pricing":"$0.08982892","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/educare"},{"rank":4,"name":"ADK","pricing":"$1.1614796","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/aidos-kuneen.png"},{"rank":5,"name":"HSR","pricing":"$4.568585","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/hshare"},{"rank":6,"name":"AST","pricing":"$0.02069479","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/airswap.png"},{"rank":7,"name":"MDT","pricing":"$0.0076097","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/measurable-data-token"},{"rank":8,"name":"MATIC","pricing":"$0.01758094","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/da9bc5822529a2c225e057c0d8d50f36.png"},{"rank":9,"name":"NMC","pricing":"$0.3965279","24_change":"","twenty_four_change":"","img":"https://www.btcmoney.cc/uploads/coin/namecoin"}]
         * description : 币指如何形成：全球币指是比特财经网研究院首创的币圈指数，是以主流交易所的主流币种为计算范围，以发行量为权数的加权综合股价指数，全球币指以11月1日为基日，以当天主流币种的市价总值为基期，基点为1000点。我们通过跟踪研究2017年5月至今的数据，通过对数据的采集、比对、清洗、加工和存储, 再利用派氏加权综合价格指数，进行计算而来。
         */

        private String description;
        private List<RisingBean> rising;
        private List<FallingBean> falling;

        @Override
        public String toString() {
            return "DataBean{" +
                    "description='" + description + '\'' +
                    ", rising=" + rising +
                    ", falling=" + falling +
                    '}';
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<RisingBean> getRising() {
            return rising;
        }

        public void setRising(List<RisingBean> rising) {
            this.rising = rising;
        }

        public List<FallingBean> getFalling() {
            return falling;
        }

        public void setFalling(List<FallingBean> falling) {
            this.falling = falling;
        }

        public static class RisingBean {
            /**
             * rank : 1
             * name : HDAC
             * pricing : $0.04575046
             * 24_change :
             * twenty_four_change :
             * img : https://www.btcmoney.cc/uploads/coin/hdac.png
             */

            private int rank;
            private String name;
            private String pricing;
            @SerializedName("24_change")
            private String _$24_change;
            private String twenty_four_change;
            private String img;

            @Override
            public String toString() {
                return "RisingBean{" +
                        "rank=" + rank +
                        ", name='" + name + '\'' +
                        ", pricing='" + pricing + '\'' +
                        ", _$24_change='" + _$24_change + '\'' +
                        ", twenty_four_change='" + twenty_four_change + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPricing() {
                return pricing;
            }

            public void setPricing(String pricing) {
                this.pricing = pricing;
            }

            public String get_$24_change() {
                return _$24_change;
            }

            public void set_$24_change(String _$24_change) {
                this._$24_change = _$24_change;
            }

            public String getTwenty_four_change() {
                return twenty_four_change;
            }

            public void setTwenty_four_change(String twenty_four_change) {
                this.twenty_four_change = twenty_four_change;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class FallingBean {
            /**
             * rank : 1
             * name : MBL
             * pricing : $0.00164767
             * 24_change :
             * twenty_four_change :
             * img : https://www.btcmoney.cc/uploads/coin/94778c5d56dc45eb657620c0e8b63517.png
             */

            private int rank;
            private String name;
            private String pricing;
            @SerializedName("24_change")
            private String _$24_change;
            private String twenty_four_change;
            private String img;

            @Override
            public String toString() {
                return "FallingBean{" +
                        "rank=" + rank +
                        ", name='" + name + '\'' +
                        ", pricing='" + pricing + '\'' +
                        ", _$24_change='" + _$24_change + '\'' +
                        ", twenty_four_change='" + twenty_four_change + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPricing() {
                return pricing;
            }

            public void setPricing(String pricing) {
                this.pricing = pricing;
            }

            public String get_$24_change() {
                return _$24_change;
            }

            public void set_$24_change(String _$24_change) {
                this._$24_change = _$24_change;
            }

            public String getTwenty_four_change() {
                return twenty_four_change;
            }

            public void setTwenty_four_change(String twenty_four_change) {
                this.twenty_four_change = twenty_four_change;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
