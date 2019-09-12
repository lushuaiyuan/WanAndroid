package com.lsy.module_mine.presenter;

import com.lsy.lib_base.base.BasePresenter;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.bean.RegistBean;
import com.lsy.lib_net.response.ResponseTransformer;
import com.lsy.lib_net.schedulers.SchedulerProvider;
import com.lsy.module_mine.contract.RegisterContract;
import com.lsy.module_mine.model.RegisterModel;
import com.lsy.module_mine.view.RegisterFragment;

import io.reactivex.functions.Consumer;

public class RegisterPresenter extends BasePresenter<RegisterFragment> implements RegisterContract.Presenter {
    private RegisterContract.Model model;

    public RegisterPresenter() {
        model = new RegisterModel();
    }

    @Override
    public void regist(String username, String password, String repassword) {
        if (!isViewAttached())
            return;
        mView.showLoading();
        model.regist(username, password, repassword)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<RegistBean>>() {
                    @Override
                    public void accept(Optional<RegistBean> registBeanOptional) throws Exception {
                        mView.hideLoading();
                        mView.onSuccess(registBeanOptional);
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
