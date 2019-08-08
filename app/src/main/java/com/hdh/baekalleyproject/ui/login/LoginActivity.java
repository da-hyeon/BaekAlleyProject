package com.hdh.baekalleyproject.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.WindowManager;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityLoginBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.setLoginActivity(this);
        mPresenter = new LoginPresenter(this, this, this);
        MyApplication.getInstance().setActivity(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initData();

        mBinding.btKakao.setOnClickListener(v->{
            mPresenter.clickKakaoLogin();
        });
    }

    private void initData(){
        mPresenter.getHashKey();
    }
}
