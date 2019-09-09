package com.ltqh.qh.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;
import java.util.List;

public class BannerEntity implements Serializable {



    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "BannerEntity{" +
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

    public static class DataBean extends SimpleBannerInfo implements Serializable {


        private int slide_id;
        private String image;
        private int status;
        private String title;
        private String url;
        private String target;
        private String content;

        @Override
        public String toString() {
            return "DataBean{" +
                    "slide_id=" + slide_id +
                    ", image='" + image + '\'' +
                    ", status=" + status +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", target='" + target + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }

        public int getSlide_id() {
            return slide_id;
        }

        public void setSlide_id(int slide_id) {
            this.slide_id = slide_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public Object getXBannerUrl() {
            return image;
        }
    }
}
