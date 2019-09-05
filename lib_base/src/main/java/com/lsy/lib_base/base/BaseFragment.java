package com.lsy.lib_base.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.scwang.smartrefresh.layout.constant.RefreshState.Loading;
import static com.scwang.smartrefresh.layout.constant.RefreshState.Refreshing;

/**
 * @author lsy
 * @create 2019/8/6 22:03
 * @Describe
 */
public abstract class BaseFragment extends Fragment {
    private final String TAG = BaseFragment.class.getName();
    private Unbinder unbinder;
    protected Activity mActivity;
    private boolean isVisibleToUser; /*标志位 判断fragment是否可见*/
    private boolean isPrepareView;/*标志位 判断view已经加载完成 避免空指针操作*/
    private boolean isInitData;/*标志位 判断数据是否初始化*/

    //  1、setUserVisibleHint方法得回调时机可能在 onCreateView 之前，也可能在 onCreateView 之后,
    //  因此，必须引入一个标志位isPrepareView判断view是否创建完成，不然，很容易会造成空指针异常。
    //  我们初始化该变量为false,在onViewCreated中，也就是view创建完成后，将其赋值为true
    //  2 数据初始化只应该加载一次，因此，引入第二个标志位，isInitData,初始为false,在数据加载完成之后，将其赋值为true。
    //  至此，我们的懒加载方法考虑了所有条件。也就是当isVisibleToUser为true，isInitData为false，isPrepareView为true时,
    //  进行数据加载，并且加载后为了防止重复调用，将isInitData赋值为true

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepareView = true;//view初始化完成
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser; //标志位保存fragment对用户的可见状态
        lazyInitData();
    }

    public abstract int getLayoutId();

    public abstract void initData();


    /**
     * 懒加载方法
     */
    private void lazyInitData() {
        if (isVisibleToUser && isPrepareView && !isInitData) {
                isInitData = true;
                initData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        //fragment生命周期中onViewCreated之后的方法 在这里调用一次懒加载 避免第一次可见不加载数据
        lazyInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isPrepareView = false;
    }

    protected void endRefresh(SmartRefreshLayout mSmartRefreshLayout) {
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.getState() == Refreshing) {
            mSmartRefreshLayout.finishRefresh();
        }
        if (mSmartRefreshLayout != null && mSmartRefreshLayout.getState() == Loading) {
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
