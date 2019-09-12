package com.lsy.module_mine.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsy.lib_base.base.BaseMvpFragment;
import com.lsy.lib_base.event.LoginEvent;
import com.lsy.lib_base.widget.AccountInputView;
import com.lsy.lib_base.widget.PasswordInputView;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.bean.RegistBean;
import com.lsy.module_mine.R;
import com.lsy.module_mine.R2;
import com.lsy.module_mine.contract.RegisterContract;
import com.lsy.module_mine.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseMvpFragment<RegisterPresenter> implements RegisterContract.View {
    @BindView(R2.id.register_account)
    AccountInputView accountInputView;
    @BindView(R2.id.register_password)
    PasswordInputView passwordInputView;
    @BindView(R2.id.register_password_again)
    PasswordInputView registerPasswordAgain;

    public static Fragment create() {
        return new RegisterFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initData() {
        mPresenter = new RegisterPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public String getUserName() {
        return accountInputView.getText();
    }

    @Override
    public String getPassword() {
        return passwordInputView.getText();
    }

    @Override
    public String getRePassword() {
        return registerPasswordAgain.getText();
    }


    @OnClick({R2.id.sv_register,R2.id.ll_go_login})
    public void viewClick(View view) {
        if(view.getId() == R.id.sv_register) {
            if (StringUtils.isEmpty(getUserName())) {
                ToastUtils.showShort("用户名不能为空");
                return;
            }
            if (StringUtils.isEmpty(getPassword())) {
                ToastUtils.showShort("密码不能为空");
                return;
            }
            if (StringUtils.isEmpty(getRePassword())) {
                ToastUtils.showShort("请确认密码");
                return;
            }
            if (view.getId() == R.id.sv_register) {
                mPresenter.regist(getUserName(), getPassword(), getRePassword());
            }
        } else if (view.getId() == R.id.ll_go_login) {
            ((LoginActivity)mActivity).changeToLogin();
        }
    }

    @Override
    public void onSuccess(Optional<RegistBean> registResponseData) {
        new LoginEvent(true).post();
        finish();
    }
}
