package com.lsy.module_home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsy.lib_base.base.BaseActivity;
import com.lsy.lib_base.utils.RouterUtils;
import com.lsy.lib_base.widget.QDWebView;
import com.lsy.module_home.R;
import com.lsy.module_home.R2;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

import butterknife.BindView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

@Route(path = RouterUtils.HOME_WEBVIEW)
public class WebActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R2.id.topbar)
    QMUITopBarLayout qmuiTopBar;
    @BindView(R2.id.webview_container)
    QMUIWebViewContainer webViewContainer;
    QDWebView mWebView;

    @Autowired
    String title;
    @Autowired
    String url;
    @Autowired
    String link;
    @Autowired
    Boolean collect;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        ARouter.getInstance().inject(this);
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

        mWebView = new QDWebView(this);
        boolean needDispatchSafeAreaInset = needDispatchSafeAreaInset();
        webViewContainer.addWebView(mWebView, needDispatchSafeAreaInset);
        if (StringUtils.isEmpty(link)) {
            mWebView.loadUrl(url);
        } else {
            mWebView.loadUrl(link);
        }
        webViewContainer.setCustomOnScrollChangeListener(new QMUIWebView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(WebView webView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                onScrollWebContent(scrollX, scrollY, oldScrollX, oldScrollY);
            }
        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            }
        });

    }
    protected boolean needDispatchSafeAreaInset() {
        return false;
    }

    protected void onScrollWebContent(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_search) {
            ToastUtils.showShort("查询");
        } else if (view.getId() == R.id.ib_more) {
            ToastUtils.showShort("更多");
        }
    }

    @Override
    protected void onDestroy() {
        webViewContainer.destroy();
        mWebView = null;
        super.onDestroy();
    }
}
