package com.hdh.baekalleyproject.ui.find_password;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityFindPasswordBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class FindPasswordActivity extends BaseActivity implements FindPasswordContract.View {

    private FindPasswordContract.Presenter mPresenter;
    private ActivityFindPasswordBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_password);
        mBinding.setFindPasswordActivity(this);
        mPresenter = new FindPasswordPresenter(this, this ,this);
        initData();


        mBinding.vDismiss.setOnClickListener(v->
                mPresenter.clickDismiss()
        );

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

        mBinding.ivClearEmail.setOnClickListener(v->
                mPresenter.clickEmailClear()
        );
    }

    private void initData() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(mBinding.tvInformation.getText().toString());
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#17B1C4")), 21, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#17B1C4")), 37, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.tvInformation.setText(ssb);
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
    public void changeSendButtonColor(boolean status) {
        if (status){
            mBinding.vSend.setAlpha(1.0f);
        } else {
            mBinding.vSend.setAlpha(0.2f);
        }
    }

    @Override
    public void changeEmailText(String text) {
        mBinding.etEmail.setText(text);
    }
}
