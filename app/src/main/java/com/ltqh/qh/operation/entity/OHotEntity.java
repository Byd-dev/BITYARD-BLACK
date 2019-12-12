package com.ltqh.qh.operation.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;
import java.util.List;

public class OHotEntity implements Serializable {


    /**
     * newsList : [{"date":"2018-10-11 12:05:04","id":19531,"thumb":"https://cdn.jin10.com/pic/ff/24c9c3b74701bbef3508c964dd139947.jpg","title":"油市飓风警报解除！今晨API已\u201c前瞻\u201d今夜EIA油价命运？"},{"date":"2018-10-02 13:14:08","id":19437,"thumb":"https://cdn.jin10.com/pic/9e/a8774fd9923f937a0445c08e2ddc5cf1.jpg","title":"能不能满足特朗普就看这次了！沙特王储去了一趟科威特 50万桶产能有望回归"},{"date":"2018-09-19 12:15:46","id":19292,"thumb":"https://cdn.jin10.com/pic/73/60dd0e5f93c6128e1d27dff7a3275cbb.jpg","title":"今年的炼厂维修季有点不一样 WTI油价未来几周可能会崩？"},{"date":"2018-09-03 12:51:06","id":19080,"thumb":"https://cdn.jin10.com/pic/b2/ad6fcde0e3817eaedd61caed2cb873cc.png","title":"如果老前辈偷偷告诉你一个交易秘笈 请先问问自己这三个问题"},{"date":"2018-09-03 06:17:17","id":19068,"thumb":"https://cdn.jin10.com/pic/c1/3713fb46c92e1bc56bec5b9cefe749cb.jpg","title":"谈崩了！特朗普周末又放狠话威胁加拿大 9月注定不太平"},{"date":"2018-08-29 12:58:21","id":19013,"thumb":"https://cdn.jin10.com/pic/11/a0aaa35ad6b55c31420f6af68accf1e1.jpg","title":"进口猛增四成！中国这支原油消费主力军重磅回归"},{"date":"2018-08-29 11:42:53","id":19012,"thumb":"https://cdn.jin10.com/pic/6f/53c9a95ab60cdbfb7d3ce039636d5863.jpg","title":"交易中是否存在\u201c一招鲜\u201d？请务必远离这些爆红的\u201c毒鸡汤\u201d！"},{"date":"2018-08-28 10:00:01","id":18996,"thumb":"https://cdn.jin10.com/pic/ce/efd0430ab77c666e989e5cb850246c0b.jpg","title":"油市大混乱将至？伊朗昨日再度宣夺这一主权引发外媒关注"},{"date":"2018-08-23 07:59:44","id":18938,"thumb":"https://cdn.jin10.com/pic/7e/cbd4e114fe5f8c79b25d4ecf42f7a2bf.png","title":"前瞻：市场热议弹劾特朗普美元真的大难临头？这件事还没完警惕千点行情再现！"},{"date":"2018-08-08 07:45:15","id":18765,"thumb":"https://cdn.jin10.com/pic/16/719852b18a38f4ab99f5162d3a0dffba.jpg","title":"前瞻：贸易风暴再起美国宣布重大消息 又一家央行登场投行教你正确交投姿态"},{"date":"2018-08-06 07:35:24","id":18739,"thumb":"https://cdn.jin10.com/pic/88/6db297344a6854ce0dcdcf0d17c3cfdb.jpg","title":"一周前瞻：美国对伊朗制裁今日重启全球市场严阵以待 本周两大数据将再造\u201c超级星期五\u201d"},{"date":"2018-08-03 12:49:50","id":18726,"thumb":"https://cdn.jin10.com/pic/77/e38d5f4e6d283161ddf55ed62b0ec73c.jpg","title":"俄罗斯7月原油产量逼近历史最高点 油市更多隐忧开始闪现"},{"date":"2018-08-03 10:14:37","id":18725,"thumb":"https://cdn.jin10.com/pic/e7/1b8895d14094b58e0338e2d47d071757.jpg","title":"平仓最能考验交易者的功力 这种平仓手法最能接近利润最大化"},{"date":"2018-07-27 21:30:00","id":18657,"thumb":"https://cdn.jin10.com/pic/8f/e03086769d97167c9567369413a8b978.jpg","title":"这一大问题即将解决 加拿大油砂卷土重来将掀起怎样的油市动乱？"},{"date":"2018-07-25 22:36:43","id":18617,"thumb":"https://cdn.jin10.com/pic/38/95df7121437723e1cece9531c5c4b74e.jpg","title":"EIA原油库存降幅远超预期油价上演过山车行情 令市场忐忑不安的是这一问题"},{"date":"2018-07-24 23:52:56","id":18600,"thumb":"https://cdn.jin10.com/pic/2d/9fba1f31a429ee58ee6a6ab71cea5240.jpg","title":"这些重要原油买家饱受货币贬值折磨 油市需求面警钟已敲响！"},{"date":"2018-07-20 22:10:31","id":18559,"thumb":"https://cdn.jin10.com/pic/7b/0fef5ac71218744d02e9f816c0bf9d50.jpg","title":"比利比亚供应中断更大的隐患已出现 未来两个月油市将陷入罕见的动荡期"},{"date":"2018-07-18 22:35:00","id":18524,"thumb":"https://cdn.jin10.com/pic/d3/0c1f14431273156907b392c21d6103a3.jpg","title":"EIA意外大增近600万桶油市炸开锅 这份报告竟还有一大噩耗？"},{"date":"2018-07-18 17:34:15","id":18520,"thumb":"https://cdn.jin10.com/pic/d7/986fc6ca164cbc50c41bd147a916668a.jpg","title":"拒绝枯竭！布油大本营焕发新生机 北海原油\u201c续命\u201d成功"},{"date":"2018-07-18 15:01:38","id":18517,"thumb":"https://cdn.jin10.com/pic/e1/830c56f47084913f58922b5c3dcf6c5c.jpg","title":"利比亚消息值每桶5美元？非也！布油狂泻7%暴露油市真正风险"}]
     * code : 200
     * resultMsg :
     */

    private int code;
    private String resultMsg;
    private List<NewsListBean> newsList;

    @Override
    public String toString() {
        return "AlertsEntity{" +
                "code=" + code +
                ", resultMsg='" + resultMsg + '\'' +
                ", newsList=" + newsList +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<NewsListBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListBean> newsList) {
        this.newsList = newsList;
    }

    public static class NewsListBean extends SimpleBannerInfo implements Serializable {
        /**
         * date : 2018-10-11 12:05:04
         * id : 19531
         * thumb : https://cdn.jin10.com/pic/ff/24c9c3b74701bbef3508c964dd139947.jpg
         * title : 油市飓风警报解除！今晨API已“前瞻”今夜EIA油价命运？
         */

        private String date;
        private int id;
        private String thumb;
        private String title;

        @Override
        public String toString() {
            return "NewsListBean{" +
                    "date='" + date + '\'' +
                    ", id=" + id +
                    ", thumb='" + thumb + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public Object getXBannerUrl() {
            return thumb;

        }
    }
}
