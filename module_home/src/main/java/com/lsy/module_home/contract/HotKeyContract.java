package com.lsy.module_home.contract;

import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.HotkeyBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;

import java.util.List;

import io.reactivex.Observable;

public interface HotKeyContract {
    interface Model {
        Observable<ResponseData<List<HotkeyBean>>> hotKeyList();
    }

    interface View extends BaseView {
        void onHotKeyResponseSuccess(Optional<List<HotkeyBean>> hotkeyResponseData);
    }

    interface Presenter {
        void hotKeyList();
    }
}
