package com.lsy.lib_net.response;

import com.lsy.lib_net.bean.Optional;

public class ResponseData<T> {

    private int errorCode;
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Optional<T> getData() {
        return new Optional<>(data);
    }

    public void setData(T data) {
        this.data = data;
    }
}
