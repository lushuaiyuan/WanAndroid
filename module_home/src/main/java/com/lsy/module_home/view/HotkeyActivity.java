package com.lsy.module_home.view;

import com.lsy.lib_base.base.BaseMvpActivity;
import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.bean.HotkeyBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_home.R;
import com.lsy.module_home.contract.HotKeyContract;
import com.lsy.module_home.presenter.HotKeyPresenter;

import java.util.List;

public class HotkeyActivity extends BaseMvpActivity<HotKeyPresenter> implements HotKeyContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_hotkey_search;
    }

    @Override
    public void init() {
        mPresenter = new HotKeyPresenter();
        mPresenter.attachView(this);
        mPresenter.hotKeyList();
    }

    @Override
    public void onHotKeyResponseSuccess(Optional<List<HotkeyBean>> hotkeyResponseData) {

    }
}
