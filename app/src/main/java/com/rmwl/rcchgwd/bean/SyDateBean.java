package com.rmwl.rcchgwd.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by acer on 2018/8/23.
 */

public class SyDateBean implements Serializable{

    private List<UrlsBean> urls;
    private List<NoticesBean> notices;
    private List<ProductBean>pros;

    public List<ProductBean> getPros() {
        return pros;
    }

    public void setPros(List<ProductBean> pros) {
        this.pros = pros;
    }

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    public List<NoticesBean> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticesBean> notices) {
        this.notices = notices;
    }

    public static class UrlsBean {
        /**
         * url : asdasdasdadsasda
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class NoticesBean {
        /**
         * content : 张三买了1玩快线的东西
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
