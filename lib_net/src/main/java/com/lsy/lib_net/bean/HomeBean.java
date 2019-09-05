package com.lsy.lib_net.bean;

import java.util.List;

public class HomeBean {
    private List<BannerBean> banners;

    private List<ArticleBean.Article> topArticles;

    private List<HotkeyBean> hotkeyBeans;

    private List<FriendBean> friendBeans;

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

    public List<HotkeyBean> getHotkeyBeans() {
        return hotkeyBeans;
    }

    public void setHotkeyBeans(List<HotkeyBean> hotkeyBeans) {
        this.hotkeyBeans = hotkeyBeans;
    }

    public List<FriendBean> getFriendBeans() {
        return friendBeans;
    }

    public void setFriendBeans(List<FriendBean> friendBeans) {
        this.friendBeans = friendBeans;
    }
}
