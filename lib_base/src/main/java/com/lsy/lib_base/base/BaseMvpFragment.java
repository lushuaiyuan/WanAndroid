package com.lsy.lib_base.base;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Lifecycle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lsy.lib_base.exception.ApiException;
import com.lsy.lib_base.exception.CustomException;
import com.lsy.lib_base.utils.RouterUtils;
import com.lsy.lib_base.widget.ProgressDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {
    protected T mPresenter;

    @Override
    public void showLoading() {
        ProgressDialog.getInstance().show(mActivity);
    }

    @Override
    public void hideLoading() {
        ProgressDialog.getInstance().dismiss();
    }


    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        if (throwable instanceof ApiException) {
            //TODO
            if (((ApiException) throwable).getCode() == CustomException.Code.FAILED_NOT_LOGIN) {
                ARouter.getInstance().build(RouterUtils.ME_LOGIN).navigation();
            }
            ToastUtils.showShort(((ApiException) throwable).getDisplayMessage());
        } else {
            ToastUtils.showShort(throwable.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}
