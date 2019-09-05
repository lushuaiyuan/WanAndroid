package com.lsy.module_home.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsy.lib_base.base.BaseFragment;
import com.lsy.lib_base.base.BaseMvpFragment;
import com.lsy.lib_base.utils.GlideImageLoader;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.HomeBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.module_home.R;
import com.lsy.module_home.R2;
import com.lsy.module_home.adapter.ArticleAdapter;
import com.lsy.module_home.contract.HomeContract;
import com.lsy.module_home.presenter.HomePresenter;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {
    @BindView(R2.id.topbar)
    QMUITopBarLayout qmuiTopBarLayout;
    @BindView(R2.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.mBanner)
    Banner mBanner;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecycleView;
    private List<ArticleBean.Article> topArticleList = new ArrayList<>();
    private List<ArticleBean.Article> articleList = new ArrayList<>();
    private List<BannerBean> bannerList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private int pageIndex = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        initView();
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mPresenter.getHomeData();
    }

    private void initView() {
        qmuiTopBarLayout.setTitle("首页");
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new ArticleAdapter(articleList);
        mRecycleView.setAdapter(articleAdapter);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                mPresenter.articleList(pageIndex);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                articleList.clear();
                topArticleList.clear();
                mPresenter.getHomeData();
            }
        });
    }

    @Override
    public void onSuccess(Optional<HomeBean> homeBeanResponseData) {
        //轮播显示
        List<BannerBean> includeNull = homeBeanResponseData.getIncludeNull().getBanners();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < includeNull.size(); i++) {
            images.add(includeNull.get(i).getImagePath());
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mPresenter.articleList(pageIndex);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        endRefresh(mSmartRefreshLayout);
    }

    @Override
    public void onSuccess1(Optional<ArticleBean> articleResponseData) {
        endRefresh(mSmartRefreshLayout);
        ArticleBean articleBean = articleResponseData.getIncludeNull();
        if (articleBean.getCurPage() == articleBean.getPageCount()) {
            mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
        }
        if (pageIndex == 0) {
            articleList.addAll(topArticleList);
        }
        articleList.addAll(articleBean.getDatas());
        articleAdapter.notifyDataSetChanged();
    }
}
