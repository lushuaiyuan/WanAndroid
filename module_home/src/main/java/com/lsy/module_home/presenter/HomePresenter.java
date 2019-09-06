package com.lsy.module_home.presenter;

import com.lsy.lib_base.base.BasePresenter;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.HomeBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.lib_net.response.ResponseTransformer;
import com.lsy.lib_net.schedulers.SchedulerProvider;
import com.lsy.module_home.contract.HomeContract;
import com.lsy.module_home.model.HomeModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private HomeContract.Model model;

    public HomePresenter() {
        this.model = new HomeModel();
    }

    @Override
    public void articleList(int pageIndex) {
        if (!isViewAttached()) {
            return;
        }
        model.getArticleList(pageIndex)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<ArticleBean>>() {
                    @Override
                    public void accept(Optional<ArticleBean> articleListOptional) throws Exception {
                        mView.onSuccess1(articleListOptional);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                    }
                });
    }

    @Override
    public void getHomeData() {
        if (!isViewAttached()) {
            return;
        }
        Observable<ResponseData<List<BannerBean>>> bannerList = model.getBannerList();
        Observable<ResponseData<List<ArticleBean.Article>>> topArticleList = model.getTopArticleList();
        Observable.zip(bannerList, topArticleList, new BiFunction<ResponseData<List<BannerBean>>, ResponseData<List<ArticleBean.Article>>, ResponseData<HomeBean>>() {
            @Override
            public ResponseData<HomeBean> apply(ResponseData<List<BannerBean>> bannerList, ResponseData<List<ArticleBean.Article>> articleList) throws Exception {
                ResponseData<HomeBean> homeResponse = new ResponseData<>();
                HomeBean homeBean = new HomeBean();
                homeBean.setBanners(bannerList.getData().getIncludeNull());
                homeBean.setTopArticles(articleList.getData().getIncludeNull());
                homeResponse.setData(homeBean);
                return homeResponse;
            }
        }).compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<Optional<HomeBean>>() {
                    @Override
                    public void accept(Optional<HomeBean> homeBeanOptional) throws Exception {
                        mView.onSuccess(homeBeanOptional);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                    }
                });


    }
}
