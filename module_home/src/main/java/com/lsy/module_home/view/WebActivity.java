package com.lsy.module_home.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ZoomButtonsController;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsy.lib_base.base.BaseActivity;
import com.lsy.lib_base.base.BaseMvpActivity;
import com.lsy.lib_base.utils.RouterUtils;
import com.lsy.lib_base.widget.QDWebView;
import com.lsy.lib_net.bean.CollectBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.module_home.R;
import com.lsy.module_home.R2;
import com.lsy.module_home.contract.WebContract;
import com.lsy.module_home.presenter.WebPresenter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

@Route(path = RouterUtils.HOME_WEBVIEW)
public class WebActivity extends BaseMvpActivity<WebPresenter> implements WebContract.View, View.OnClickListener {
    @BindView(R2.id.topbar)
    QMUITopBarLayout qmuiTopBar;
    @BindView(R2.id.webview_container)
    QMUIWebViewContainer webViewContainer;
    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;
    QDWebView mWebView;

    private ProgressHandler mProgressHandler;
    private final static int PROGRESS_PROCESS = 0;
    private final static int PROGRESS_GONE = 1;
    private String title;
    private String url;
    private String link;
    private Boolean collect;
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        mPresenter = new WebPresenter();
        mPresenter.attachView(this);

        mProgressHandler = new ProgressHandler(this);
        Bundle extras = getIntent().getExtras();
        link = extras.getString("link");
        url = extras.getString("url");
        title = extras.getString("title");
        collect = extras.getBoolean("collect");
        id = extras.getInt("id");
        initTopBar();
        initWebView();
    }

    private void initWebView() {
        mWebView = new QDWebView(this);
        webViewContainer.addWebView(mWebView, true);
        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.requestFocus(View.FOCUS_DOWN);
        setZoomControlGone(mWebView);
        configWebView(webViewContainer, mWebView);
        if (StringUtils.isEmpty(link)) {
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl(link);
        }
    }

    private void initTopBar() {
        qmuiTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        qmuiTopBar.setTitle(title);
        View view = LayoutInflater.from(this).inflate(R.layout.view_webview_right, null);
        ImageButton ibSearch = view.findViewById(R.id.ib_search);
        ibSearch.setOnClickListener(this);
        ImageButton ibMore = view.findViewById(R.id.ib_more);
        ibMore.setOnClickListener(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        qmuiTopBar.addRightView(view, R.id.home_right, layoutParams);
    }

    protected void configWebView(QMUIWebViewContainer webViewContainer, QMUIWebView webView) {
        webView.setCallback(new QMUIWebView.Callback() {
            @Override
            public void onSureNotSupportChangeCssEnv() {
                new QMUIDialog.MessageDialogBuilder(WebActivity.this)
                        .setMessage("不支持更改css环境")
                        .addAction(new QMUIDialogAction(WebActivity.this, "确定", new QMUIDialogAction.ActionListener() {

                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }))
                        .show();
            }
        });
    }

    public static void setZoomControlGone(WebView webView) {
        webView.getSettings().setDisplayZoomControls(false);
        @SuppressWarnings("rawtypes")
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController zoomButtonsController = new ZoomButtonsController(
                    webView);
            zoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(webView, zoomButtonsController);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected WebChromeClient getWebViewChromeClient() {
        return new ExplorerWebViewChromeClient();
    }

    protected QMUIWebViewClient getWebViewClient() {
        return new ExplorerWebViewClient(true);
    }

    @Override
    public void onSuccess(Optional<CollectBean> collectBean) {

    }

    private class ExplorerWebViewChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 修改进度条
            if (newProgress > mProgressHandler.mDstProgressIndex) {
                sendProgressMessage(PROGRESS_PROCESS, newProgress, 100);
            }
        }
    }

    private class ExplorerWebViewClient extends QMUIWebViewClient {

        public ExplorerWebViewClient(boolean needDispatchSafeAreaInset) {
            super(needDispatchSafeAreaInset, true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PROGRESS_PROCESS, 30, 500);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            sendProgressMessage(PROGRESS_GONE, 100, 0);
        }
    }

    private void sendProgressMessage(int progressType, int newProgress, int duration) {
        Message msg = new Message();
        msg.what = progressType;
        msg.arg1 = newProgress;
        msg.arg2 = duration;
        mProgressHandler.sendMessage(msg);
    }


    private static class ProgressHandler extends Handler {
        private final WeakReference<Activity> mActivityReference;

        ProgressHandler(Activity activity) {
            this.mActivityReference = new WeakReference<>(activity);
        }

        private int mDstProgressIndex;
        private int mDuration;
        private ObjectAnimator mAnimator;


        @Override
        public void handleMessage(Message msg) {
            WebActivity webActivity = (WebActivity) mActivityReference.get();
            if (webActivity == null)
                return;
            switch (msg.what) {
                case PROGRESS_PROCESS:
                    mDstProgressIndex = msg.arg1;
                    mDuration = msg.arg2;
                    webActivity.progressBar.setVisibility(View.VISIBLE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(webActivity.progressBar, "progress", mDstProgressIndex);
                    mAnimator.setDuration(mDuration);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (webActivity.progressBar.getProgress() == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500);
                            }
                        }
                    });
                    mAnimator.start();
                    break;
                case PROGRESS_GONE:
                    mDstProgressIndex = 0;
                    mDuration = 0;
                    webActivity.progressBar.setProgress(0);
                    webActivity.progressBar.setVisibility(View.GONE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(webActivity.progressBar, "progress", 0);
                    mAnimator.setDuration(0);
                    mAnimator.removeAllListeners();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_search) {
            ARouter.getInstance().build(RouterUtils.ME_LOGIN).navigation();
        } else if (view.getId() == R.id.ib_more) {
            initListPopupIfNeed();
            mListPopup.showAsDropDown(view, 0, QMUIDisplayHelper.dp2px(this, 10));
        } else if (view.getId() == R.id.txt_collect) {
            mListPopup.dismiss();
            if (collect) {
                mPresenter.cancelCollected1(id);
            } else {
                mPresenter.collectIn(id);
            }
        } else if (view.getId() == R.id.txt_share) {
            ToastUtils.showShort("分享");
            mListPopup.dismiss();
        } else if (view.getId() == R.id.txt_lookinbrowser) {
            ToastUtils.showShort("在浏览器中打开");
            mListPopup.dismiss();
        }
    }

    private PopupWindow mListPopup;

    private void initListPopupIfNeed() {
        View popView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        popView.findViewById(R.id.txt_share).setOnClickListener(this);
        popView.findViewById(R.id.txt_collect).setOnClickListener(this);
        popView.findViewById(R.id.txt_lookinbrowser).setOnClickListener(this);
        mListPopup = new PopupWindow(this);
        mListPopup.setContentView(popView);
        mListPopup.setWidth(QMUIDisplayHelper.dp2px(this, 200));
        mListPopup.setHeight(WRAP_CONTENT);
        mListPopup.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        mListPopup.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mListPopup.setOutsideTouchable(true);
    }

    @Override
    protected void onDestroy() {
        webViewContainer.destroy();
        mWebView = null;
        mProgressHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
