package com.lsy.lib_net;

import com.google.gson.Gson;
import com.lsy.lib_net.interceptor.AddCookiesInterceptor;
import com.lsy.lib_net.interceptor.LogInterceptor;
import com.lsy.lib_net.interceptor.ReceivedCookiesInterceptor;
import com.lsy.lib_net.request.APIService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lsy.lib_net.BuildConfig.DEBUG;


public class NetWorkManager {
    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile APIService apiService = null;


    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        //初始化OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (DEBUG) {
            LogInterceptor logInterceptor = new LogInterceptor();
            builder.addInterceptor(logInterceptor);
        }
        //TODO 可以配置拦截头  addInterceptor()还有很多的用法，比如：封装一些公共的参数等等。参考如下博客：http://blog.csdn.net/jdsjlzx/article/details/52063950
//        builder.addInterceptor(new HttpBaseParamsLoggingInterceptor());
        builder.addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor());
        OkHttpClient client = builder.build();

        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIService.HOST)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static APIService getApiService() {
        if (apiService == null) {
            synchronized (APIService.class) {
                apiService = retrofit.create(APIService.class);
            }
        }
        return apiService;
    }

}
