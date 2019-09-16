package com.lsy.module_home.view;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lsy.lib_base.base.BaseMvpFragment;
import com.lsy.lib_base.utils.GlideImageLoader;
import com.lsy.lib_base.utils.RouterUtils;
import com.lsy.lib_net.bean.ArticleBean;
import com.lsy.lib_net.bean.BannerBean;
import com.lsy.lib_net.bean.CollectBean;
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
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = RouterUtils.HOME_FRAGMENT_MAIN)
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnBannerListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {
    @BindView(R2.id.topbar)
    QMUITopBarLayout qmuiTopBarLayout;
    @BindView(R2.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecycleView;
    Banner mBanner;
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
        showLoading();
        mPresenter.getHomeData();
    }

    private void initView() {
        qmuiTopBarLayout.setTitleGravity(Gravity.LEFT);
        qmuiTopBarLayout.setTitle("首页");
        qmuiTopBarLayout.addRightImageButton(R.mipmap.ic_search, 0).setOnClickListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        articleAdapter = new ArticleAdapter(articleList);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, null);
        mBanner = view.findViewById(R.id.mBanner);
        mBanner.setBannerAnimation(Transformer.Accordion);
        mBanner.setOnBannerListener(this);
        articleAdapter.addHeaderView(view, 0);
        articleAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecycleView.setAdapter(articleAdapter);
        articleAdapter.setOnItemClickListener(this);
        articleAdapter.setOnItemChildClickListener(this);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                mPresenter.articleList(pageIndex);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                mPresenter.getHomeData();
            }
        });
    }

    @Override
    public void onSuccess(Optional<HomeBean> homeBeanResponseData) {
        hideLoading();
        //轮播显示
        bannerList = homeBeanResponseData.getIncludeNull().getBanners();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            images.add(bannerList.get(i).getImagePath());
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        if (pageIndex == 0) {
            articleList.clear();
        }
        topArticleList = homeBeanResponseData.getIncludeNull().getTopArticles();
        mPresenter.articleList(pageIndex);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        hideLoading();
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

    /**
     * 收藏成功
     *
     * @param collectResonseData
     */
    @Override
    public void onSuccess2(Optional<CollectBean> collectResonseData) {
        if (isCollect) {
            articleList.get(position).setCollect(true);
        } else {
            articleList.get(position).setCollect(false);
        }
        articleAdapter.notifyItemChanged(position);
    }

    @Override
    public void OnBannerClick(int position) {

        ARouter.getInstance().build(RouterUtils.HOME_WEBVIEW)
                .withString("title", bannerList.get(position).getTitle())
                .withString("url", bannerList.get(position).getUrl())
                .withInt("id", bannerList.get(position).getId())
                .navigation();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build(RouterUtils.HOME_WEBVIEW)
                .withString("title", articleList.get(position).getTitle())
                .withString("link", articleList.get(position).getLink())
                .withBoolean("collect", articleList.get(position).isCollect())
                .withInt("id", articleList.get(position).getId())
                .navigation();
    }

    private int position;
    private boolean isCollect;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        this.position = position;
        if (articleList.get(position).isCollect()) {
            isCollect = false;
            mPresenter.cancelCollect(articleList.get(position).getId());
        } else {
            isCollect = true;
            mPresenter.collect(articleList.get(position).getId());
        }
    }

    @Override
    public void onClick(View view) {
        ARouter.getInstance().build(RouterUtils.HOTKEY_SEARCH).navigation();
    }
}
