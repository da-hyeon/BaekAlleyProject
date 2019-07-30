package com.hdh.baekalleyproject.ui.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityFilterBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class FilterActivity extends BaseActivity implements FilterContract.View {

    private FilterContract.Presenter mPresenter;
    private ActivityFilterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_filter);
        mBinding.setFilterActivity(this);

        initData();

        //닫기 버튼 클릭
        mBinding.vDismiss.setOnClickListener(v->{
            mPresenter.clickDismiss();
        });
    }

    private void initData(){
        mPresenter = new FilterPresenter(this, this ,this);
    }
}
