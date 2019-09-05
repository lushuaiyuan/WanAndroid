package com.lsy.lib_net;

import android.app.Application;

import com.lsy.lib_base.IComponentApplication;

public class NetApplicaiton implements IComponentApplication {
    @Override
    public void init(Application application) {
        NetWorkManager.getInstance().init();
    }
}
