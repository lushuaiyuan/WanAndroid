package com.lsy.lib_net.request;


import android.graphics.Color;

import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.CollectArticle;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.bean.CollectedWebSite;
import com.lsy.lib_net.bean.FriendBean;
import com.lsy.lib_net.bean.HotkeyBean;
import com.lsy.lib_net.bean.LogOutBean;
import com.lsy.lib_net.bean.LoginBean;
import com.lsy.lib_net.bean.RegistBean;
import com.lsy.lib_net.response.ResponseData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    public static final String HOST = "https://www.wanandroid.com/";

    //注册
    @FormUrlEncoded
    @POST("user/register")
    Observable<ResponseData<RegistBean>> regist(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResponseData<LoginBean>> login(@Field("username") String username, @Field("password") String password);

    //退出
    @GET("user/logout/json")
    Observable<ResponseData<LogOutBean>> logout();

    //首页轮播图
    @GET("banner/json")
    Observable<ResponseData<List<BannerBean>>> getBannerList();

    //分页查询首页文章列表
    @GET("article/list/{pageIndex}/json")
    Observable<ResponseData<ArticleBean>> getArticleList(@Path("pageIndex") int pageIndex);

    //获取热搜列表
    @GET("hotkey/json")
    Observable<ResponseData<List<HotkeyBean>>> getHotKeyList();

    //常用网站列表
    @GET("friend/json")
    Observable<ResponseData<List<FriendBean>>> getFriendList();

    // 置顶文章
    @GET("article/top/json")
    Observable<ResponseData<List<ArticleBean.Article>>> getTopArticLeList();

    //收藏文章列表
    @GET("lg/collect/list/{pageIndex}/json")
    Observable<ResponseData<List<CollectArticle>>> getCollectArticles(@Path("pageIndex") int pageIndex);

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<ResponseData<CollectBean>> collectIn(@Path("id") int id);

    //收藏站外文章方法1
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<ResponseData<CollectBean>> collectOut(@FieldMap Map<String, String> paramsMap);

    //收藏站外文章方法2
    @FormUrlEncoded
    @POST("lg/collect/add/json")
    Observable<ResponseData<CollectBean>> collectOut(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    //文章列表 取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResponseData<CollectBean>> cancelCollected1(@Path("id") int id);

    //我的收藏页面（该页面包含自己录入的内容）
    @POST("lg/uncollect/{id}/json")
    Observable<ResponseData<CollectBean>> cancelCollected2(@Path("id") int id);


    //收藏得网站列表
    @GET("lg/collect/usertools/json")
    Observable<ResponseData<CollectedWebSite>> getCollectWebsites();

    //编辑收藏网站  参数：id,name,link
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Observable<ResponseData<CollectedWebSite>> updateWesite(@FieldMap Map<String, String> paramsMap);

    //删除收藏网站
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Observable<ResponseData<CollectBean>> deleteWesite(@Field("id") int id);
}
