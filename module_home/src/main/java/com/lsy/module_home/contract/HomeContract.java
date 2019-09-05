package com.lsy.module_home.contract;

import android.database.Observable;

import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.response.ResponseData;

import java.util.List;

public interface HomeContract {
    interface Model{
        /**
         * 首页轮播图
         * @return
         */
        Observable<ResponseData<BannerBean>> getBannerList();

        Observable<ResponseData<List<ArticleBean.Article>>> getArticleList();
    }

    interface View extends BaseView {

    }

    interface Presenter  {

    }
}
