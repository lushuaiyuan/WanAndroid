package com.lsy.module_mine.presenter;

import com.lsy.lib_base.base.BasePresenter;
import com.lsy.lib_net.bean.LoginBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseTransformer;
import com.lsy.lib_net.schedulers.SchedulerProvider;
import com.lsy.module_mine.contract.LoginContract;
import com.lsy.module_mine.model.LoginModel;
import com.lsy.module_mine.view.LoginFragment;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginFragment> implements LoginContract.Presenter {
    private LoginContract.Model model;

    public LoginPresenter() {
        this.model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        if (!isViewAttached())
            return;
        model.login(username, password)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<LoginBean>>() {
                    @Override
                    public void accept(Optional<LoginBean> loginBeanOptional) throws Exception {
                        mView.hideLoading();
                        mView.onSuccess(loginBeanOptional);
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
