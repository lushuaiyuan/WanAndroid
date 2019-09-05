package com.lsy.lib_net.interceptor;


import com.blankj.utilcode.util.SPUtils;
import com.lsy.lib_base.utils.Constant;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 非首次请求
 */
public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = (HashSet<String>) SPUtils.getInstance(Constant.SPNANEM).getStringSet(Constant.COOKIE);
        if (cookies != null) {
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
