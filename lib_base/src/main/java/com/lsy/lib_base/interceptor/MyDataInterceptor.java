package com.lsy.lib_base.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.lsy.lib_base.utils.Constant;
import com.lsy.lib_base.utils.RouterUtils;

/**
 * 添加拦截器的时候，建议clean再打包运行，不然会出现，无效的情况
 * 切记一个项目里面priority的值保证唯一，不然会出毛病
 */
@Interceptor(priority = 1, name = "重新分组进行拦截")

public class MyDataInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getGroup().equals("needLogin")) {
            //已经处于登录状态
            if (SPUtils.getInstance().getStringSet(Constant.COOKIE).size() > 0) {//不需要登录
                callback.onContinue(postcard);//直接执行
            } else {//需要登录
                Log.e("lsy", "需要去登陆");
                ARouter.getInstance().build(RouterUtils.ME_LOGIN)
                        .withString("path", postcard.getPath()).navigation();
            }

            //直接拦截,走onLost方法
//           callback.onInterrupt(null);

            //添加数据
//            postcard.withString("extra", "我是在拦截器中附加的参数");
//            callback.onContinue(postcard);

//            callback.onInterrupt(null);
//
//            Log.e("拦截的地址----------", postcard.getPath());
//            Log.e("拦截的地址分组----------", postcard.getGroup());
//            ARouter.getInstance().build(RouterUtils.ME_LOGIN)
//                    .withString("path", postcard.getPath()).navigation();
        } else {
//            postcard.withString("path", "我是在拦截器中附加的参数，拦截的地址为：" + postcard.getPath());

            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
