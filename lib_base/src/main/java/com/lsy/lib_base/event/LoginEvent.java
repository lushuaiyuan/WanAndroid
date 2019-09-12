package com.lsy.lib_base.event;

public class LoginEvent extends BaseEvent {
    private boolean login;

    public LoginEvent(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
