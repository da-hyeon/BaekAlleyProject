package com.hdh.baekalleyproject.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.WindowManager;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityLoginBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;
import com.kakao.auth.Session;

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

        mBinding.btKakao.setOnClickListener(v->
            mPresenter.clickKakaoLogin()
            //mBinding.btKakaoLogin.performClick()
        );

        mBinding.btKakaoLogin.setOnClickListener(v->
                mPresenter.clickKakaoLogin()
        );

        mBinding.btEmail.setOnClickListener(v->{
            mPresenter.clickEmailLogin();
        });
    }

    private void initData(){
        mPresenter.getHashKey();
        mPresenter.initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
