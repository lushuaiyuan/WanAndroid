package com.lsy.module_mine.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lsy.lib_base.base.BaseActivity;
import com.lsy.lib_base.utils.RouterUtils;
import com.lsy.module_mine.R;
import com.lsy.module_mine.R2;
import com.lsy.module_mine.adapter.FixedFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;

@Route(path = RouterUtils.ME_LOGIN)
public class LoginActivity extends BaseActivity {
    @BindView(R2.id.topbar)
    QMUITopBarLayout qmuiTopBarLayout;
    @BindView(R2.id.vp)
    ViewPager mViewPager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public void changeToRegister() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(1);
        }
    }

    public void changeToLogin() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(0);
        }
    }
    @Override
    public void init() {
        qmuiTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FixedFragmentPagerAdapter adapter = new FixedFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        adapter.setFragmentList(LoginFragment.create(), RegisterFragment.create());
    }
}
