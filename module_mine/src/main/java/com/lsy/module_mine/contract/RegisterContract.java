package com.lsy.module_mine.contract;

import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.bean.RegistBean;
import com.lsy.lib_net.response.ResponseData;

import io.reactivex.Observable;

public interface RegisterContract {
    interface Model {
        Observable<ResponseData<RegistBean>> regist(String username, String password, String repassword);
    }

    interface View extends BaseView {
        String getUserName();

        String getPassword();

        String getRePassword();

        void onSuccess(Optional<RegistBean> registResponseData);
    }

    interface Presenter {
        void regist(String username, String password, String repassword);
    }
}
