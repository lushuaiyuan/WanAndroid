package com.lsy.module_home.presenter;

import com.lsy.lib_base.base.BasePresenter;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseTransformer;
import com.lsy.lib_net.schedulers.SchedulerProvider;
import com.lsy.module_home.contract.WebContract;
import com.lsy.module_home.model.WebModel;

import java.util.Map;

import io.reactivex.functions.Consumer;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {
    private WebContract.Model model;

    public WebPresenter() {
        this.model = new WebModel();
    }

    @Override
    public void collectIn(int id) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.collectIn(id)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<CollectBean>>() {
                    @Override
                    public void accept(Optional<CollectBean> collectBeanOptional) throws Exception {
                        mView.hideLoading();
                        mView.onSuccess(collectBeanOptional);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onError(throwable);
                    }
                });
    }

    @Override
    public void collectOut(Map<String, String> params) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();

        model.collectOut(params)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<CollectBean>>() {
                    @Override
                    public void accept(Optional<CollectBean> collectBeanOptional) throws Exception {
                        mView.hideLoading();
                        mView.onSuccess(collectBeanOptional);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onError(throwable);
                    }
                });
    }

    @Override
    public void cancelCollected1(int id) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.cancelCollected1(id)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<CollectBean>>() {
                    @Override
                    public void accept(Optional<CollectBean> collectBeanOptional) throws Exception {
                        mView.hideLoading();
                        mView.onSuccess(collectBeanOptional);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onError(throwable);
                    }
                });
    }
}
