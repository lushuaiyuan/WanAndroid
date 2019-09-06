package com.lsy.lib_base.exception;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.lsy.lib_base.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLException;

import retrofit2.HttpException;

public class CustomException {


    public static ApiException handleException(Throwable e) {
        int code = onGetCode(e);
        return new ApiException(code, onGetMsg(code));
    }

    private static int onGetCode(Throwable e) {
        if (!NetworkUtils.isConnected()) {
            return Code.NET;
        } else {
            if (e instanceof SocketTimeoutException) {
                return Code.TIMEOUT;
            } else if (e instanceof HttpException) {
                return Code.HTTP;
            } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
                return Code.HOST;
            } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException) {
                return Code.JSON;
            } else if (e instanceof SSLException) {
                return Code.SSL;
            } else {
                return Code.UNKNOWN;
            }
        }
    }

    /**
     * 重写该方法去返回错误码对应的错误信息
     *
     * @param code 错误码
     * @return 错误信息
     */
    private static String onGetMsg(int code) {
        String msg;
        switch (code) {
            default:
                msg = StringUtils.getString(R.string.error_default);
                break;
            case Code.NET:
                msg = StringUtils.getString(R.string.error_net);
                break;
            case Code.TIMEOUT:
                msg = StringUtils.getString(R.string.error_timeout);
                break;
            case Code.JSON:
                msg = StringUtils.getString(R.string.error_json);
                break;
            case Code.HTTP:
                msg = StringUtils.getString(R.string.error_http);
                break;
            case Code.HOST:
                msg = StringUtils.getString(R.string.error_host);
                break;
            case Code.SSL:
                msg = StringUtils.getString(R.string.error_SSL);
                break;
        }
        return msg;
    }

    public interface Code {
        int UNKNOWN = -1;
        int NET = 0;
        int TIMEOUT = 1;
        int JSON = 2;
        int HTTP = 3;
        int HOST = 4;
        int SSL = 5;
    }
}
