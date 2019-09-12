package com.lsy.module_home.model;


import com.lsy.lib_net.NetWorkManager;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_home.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;

public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<ResponseData<List<BannerBean>>> getBannerList() {
        return NetWorkManager.getApiService().getBannerList();
    }

    @Override
    public Observable<ResponseData<ArticleBean>> getArticleList(int pageIndex) {
        return NetWorkManager.getApiService().getArticleList(pageIndex);
    }

    @Override
    public Observable<ResponseData<List<ArticleBean.Article>>> getTopArticleList() {
        return NetWorkManager.getApiService().getTopArticLeList();
    }

    @Override
    public Observable<ResponseData<CollectBean>> collect(int id) {
        return NetWorkManager.getApiService().collectIn(id);
    }

    @Override
    public Observable<ResponseData<CollectBean>> cancelCollect(int id) {
        return NetWorkManager.getApiService().cancelCollected1(id);
    }
}
