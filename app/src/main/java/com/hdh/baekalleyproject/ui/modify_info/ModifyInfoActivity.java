package com.hdh.baekalleyproject.ui.modify_info;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityModifyInfoBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class ModifyInfoActivity extends BaseActivity implements ModifyInfoContract.View {

    private ActivityModifyInfoBinding mBinding;
    private ModifyInfoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_modify_info);
        mBinding.setModifyInfoActivity(this);
        mPresenter = new ModifyInfoPresenter(this, this, this);

        initData();

        mBinding.vDismiss.setOnClickListener(v->
            mPresenter.clickDismiss()
        );
    }

    private void initData() {
        mPresenter.setView(mBinding.rvWrongItem);
    }
}
