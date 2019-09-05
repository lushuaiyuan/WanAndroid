package com.lsy.lib_net.interceptor;


import com.blankj.utilcode.util.SPUtils;
import com.lsy.lib_base.utils.Constant;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 首次请求的处理
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPUtils.getInstance(Constant.SPNANEM).put(Constant.COOKIE, cookies);
        }
        return response;
    }
}
