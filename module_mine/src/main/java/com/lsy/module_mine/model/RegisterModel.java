package com.lsy.module_mine.model;

import com.lsy.lib_net.NetWorkManager;
import com.lsy.lib_net.bean.RegistBean;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_mine.contract.RegisterContract;

import io.reactivex.Observable;

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<ResponseData<RegistBean>> regist(String username, String password, String repassword) {
        return NetWorkManager.getApiService().regist(username,password,repassword);
    }
}
