package com.hdh.baekalleyproject.ui.login_email;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityLoginEmailBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class LoginEmailActivity extends BaseActivity implements LoginEmailContract.View {

    private LoginEmailContract.Presenter mPresenter;
    private ActivityLoginEmailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_email);
        mBinding.setLoginEmailActivity(this);
        mPresenter = new LoginEmailPresenter(this, this, this);

        initData();

        mBinding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.enteringEmailText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.enteringPasswordText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.ivClearEmail.setOnClickListener(v->
                mPresenter.clickEmailClear()
        );

        mBinding.ivClearPassword.setOnClickListener(v->
                mPresenter.clickPasswordClear()
        );

        mBinding.vDismiss.setOnClickListener(v->
                mPresenter.clickDismiss()
        );

        mBinding.tvFindPassword.setOnClickListener(v->
                mPresenter.clickFindPassword()
        );

        mBinding.tvRegistration.setOnClickListener(v->
                mPresenter.clickRegistration()
        );
    }

    private void initData() {

    }

    @Override
    public void changeEmailClearButton(boolean status) {
        if (status){
            mBinding.ivClearEmail.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivClearEmail.setVisibility(View.GONE);
        }
    }

    @Override
    public void changePasswordClearButton(boolean status) {
        if (status){
            mBinding.ivClearPassword.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivClearPassword.setVisibility(View.GONE);
        }
    }

    @Override
    public void changeLoginButtonColor(boolean status) {
        if (status){
            mBinding.vLogin.setAlpha(1.0f);
        } else {
            mBinding.vLogin.setAlpha(0.2f);
        }
    }

    @Override
    public void changeEmailText(String text) {
        mBinding.etEmail.setText(text);
    }

    @Override
    public void changePasswordText(String text) {
        mBinding.etPassword.setText(text);
    }

   // @Override
   // public void onBackPressed() {
   //     mPresenter.clickDismiss();
   // }
}
