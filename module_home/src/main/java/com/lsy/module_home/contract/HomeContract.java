package com.lsy.module_home.contract;


import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.CollectBean;
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

        /**
         * 收藏
         *
         * @param id
         * @return
         */
        Observable<ResponseData<CollectBean>> collect(int id);

        /**
         * 取消收藏
         *
         * @param id
         * @return
         */
        Observable<ResponseData<CollectBean>> cancelCollect(int id);
    }

    interface View extends BaseView {
        void onSuccess(Optional<HomeBean> homeBeanResponseData);

        void onSuccess1(Optional<ArticleBean> articleResponseData);

        void onSuccess2(Optional<CollectBean> collectResonseData);

    }

    interface Presenter {
        void articleList(int pageIndex);

        void getHomeData();

        void collect(int id);

        void cancelCollect(int id);
    }
}
