package com.lsy.module_home.contract;


import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.HomeBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {
    interface Model {
        /**
         * 首页轮播图
         *
         * @return
         */
        Observable<ResponseData<List<BannerBean>>> getBannerList();

        /**
         * 获取文章列表
         *
         * @return
         */
        Observable<ResponseData<ArticleBean>> getArticleList(int pageIndex);

        /**
         * 获取顶部文章列表
         *
         * @return
         */
        Observable<ResponseData<List<ArticleBean.Article>>> getTopArticleList();
    }

    interface View extends BaseView {
        void onSuccess(Optional<HomeBean> homeBeanResponseData);

        void onSuccess1(Optional<ArticleBean> articleResponseData);
    }

    interface Presenter {
        void articleList(int pageIndex);

        void getHomeData();
    }
}
