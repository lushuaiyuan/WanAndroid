package com.lsy.module_home.model;

import com.lsy.lib_net.NetWorkManager;
import com.lsy.lib_net.bean.HotkeyBean;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_home.contract.HotKeyContract;

import java.util.List;

import io.reactivex.Observable;

public class HotKeyModel implements HotKeyContract.Model {
    @Override
    public Observable<ResponseData<List<HotkeyBean>>> hotKeyList() {
        return NetWorkManager.getApiService().getHotKeyList();
    }
}
