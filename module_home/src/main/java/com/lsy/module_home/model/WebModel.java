package com.lsy.module_home.model;

import com.lsy.lib_net.NetWorkManager;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_home.contract.WebContract;

import java.util.Map;

import io.reactivex.Observable;

public class WebModel implements WebContract.Model {
    @Override
    public Observable<ResponseData<CollectBean>> collectIn(int id) {
        return NetWorkManager.getApiService().collectIn(id);
    }

    @Override
    public Observable<ResponseData<CollectBean>> collectOut(Map<String, String> params) {
        return NetWorkManager.getApiService().collectOut(params);
    }

    @Override
    public Observable<ResponseData<CollectBean>> cancelCollected1(int id) {
        return NetWorkManager.getApiService().cancelCollected1(id);
    }
}
