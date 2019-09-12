package com.lsy.module_mine.view;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsy.lib_base.base.BaseMvpFragment;
import com.lsy.lib_base.event.LoginEvent;
import com.lsy.lib_base.widget.AccountInputView;
import com.lsy.lib_base.widget.PasswordInputView;
import com.lsy.lib_net.bean.LoginBean;
import com.lsy.lib_net.bean.Optional;
import com.lsy.lib_net.response.ResponseData;
import com.lsy.module_mine.R;
import com.lsy.module_mine.R2;
import com.lsy.module_mine.contract.LoginContract;
import com.lsy.module_mine.presenter.LoginPresenter;

import java.net.PasswordAuthentication;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseMvpFragment<LoginPresenter> implements LoginContract.View {
    @BindView(R2.id.login_account)
    AccountInputView loginAccount;
    @BindView(R2.id.login_password)
    PasswordInputView loginPassword;

    public static Fragment create() {
        return new LoginFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public String getUserName() {
        return loginAccount.getText();
    }

    @Override
    public String getPassword() {
        return loginPassword.getText();
    }

    @OnClick({R2.id.sv_login,R2.id.ll_go_register})
    public void viewClick(View view) {
        if (view.getId() == R.id.sv_login) {
            if (StringUtils.isEmpty(getUserName())) {
                ToastUtils.showShort("用户名不能为空");
                return;
            }
            if (StringUtils.isEmpty(getPassword())) {
                ToastUtils.showShort("密码不能为空");
                return;
            }
            mPresenter.login(getUserName(), getPassword());
        } else if (view.getId() == R.id.ll_go_register) {
            ((LoginActivity)mActivity).changeToRegister();
        }
    }

    @Override
    public void onSuccess(Optional<LoginBean> loginResponseData) {
        new LoginEvent(true).post();
        finish();
    }
}
