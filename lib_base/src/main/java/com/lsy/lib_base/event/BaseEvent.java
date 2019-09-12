package com.lsy.lib_base.event;

import org.greenrobot.eventbus.EventBus;

public class BaseEvent {
    public void post() {
        EventBus.getDefault().post(this);
    }
}
