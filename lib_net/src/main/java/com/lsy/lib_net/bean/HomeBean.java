package com.lsy.lib_net.bean;

import java.util.List;

public class HomeBean {
    private List<BannerBean> banners;

    private List<ArticleBean.Article> topArticles;


    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<ArticleBean.Article> getTopArticles() {
        return topArticles;
    }

    public void setTopArticles(List<ArticleBean.Article> topArticles) {
        this.topArticles = topArticles;
    }
}
