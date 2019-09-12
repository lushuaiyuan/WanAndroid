package com.lsy.module_mine.contract;

import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.LoginBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;

import io.reactivex.Observable;

public interface LoginContract {
    interface Model {
        Observable<ResponseData<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView {
        String getUserName();

        String getPassword();

        void onSuccess(Optional<LoginBean> loginResponseData);
    }

    interface Presenter {
        void login(String username, String password);
    }
}
