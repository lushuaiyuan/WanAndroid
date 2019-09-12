package com.lsy.module_home.contract;

import com.lsy.lib_base.base.BaseView;
import com.lsy.lib_net.NetWorkManager;
import com.lsy.lib_net.bean.CollectArticle;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;

import java.util.Map;

import io.reactivex.Observable;

public interface WebContract {
    interface Model {
        //收藏站内文章
        Observable<ResponseData<CollectBean>> collectIn(int id);

        //收藏站外文章
        Observable<ResponseData<CollectBean>> collectOut(Map<String, String> params);

        //取消收藏
        Observable<ResponseData<CollectBean>> cancelCollected1(int id);
    }

    interface View extends BaseView {
        void onSuccess(Optional<CollectBean> collectBean);
    }

    interface Presenter {
        void collectIn(int id);

        void collectOut(Map<String, String> params);

        void cancelCollected1(int id);
    }
}
