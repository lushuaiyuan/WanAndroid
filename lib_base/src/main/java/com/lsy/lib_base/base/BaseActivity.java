package com.lsy.lib_base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.scwang.smartrefresh.layout.constant.RefreshState.Loading;
import static com.scwang.smartrefresh.layout.constant.RefreshState.Refreshing;

public abstract class BaseActivity extends AppCompatActivity {
    Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ARouter.getInstance().inject(this);
        bind = ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        init();
    }

    public abstract int getLayoutId();

    public abstract void init();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    public void enRefresh(SmartRefreshLayout mSmartRefreshLayout) {
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.getState() == Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.getState() == Loading) {
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
