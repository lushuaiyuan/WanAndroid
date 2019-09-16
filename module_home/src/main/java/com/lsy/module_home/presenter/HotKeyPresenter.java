package com.lsy.module_home.presenter;

import com.lsy.lib_base.base.BasePresenter;
import com.lsy.lib_net.bean.HotkeyBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseTransformer;
import com.lsy.lib_net.schedulers.SchedulerProvider;
import com.lsy.module_home.contract.HotKeyContract;
import com.lsy.module_home.model.HotKeyModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HotKeyPresenter extends BasePresenter<HotKeyContract.View> implements HotKeyContract.Presenter {
    private HotKeyContract.Model model;

    public HotKeyPresenter() {
        this.model = new HotKeyModel();
    }

    @Override
    public void hotKeyList() {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.hotKeyList().compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .as(mView.bindAutoDispose())
                .subscribe(new Consumer<Optional<List<HotkeyBean>>>() {
                    @Override
                    public void accept(Optional<List<HotkeyBean>> listOptional) throws Exception {
                        mView.onHotKeyResponseSuccess(listOptional);
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
