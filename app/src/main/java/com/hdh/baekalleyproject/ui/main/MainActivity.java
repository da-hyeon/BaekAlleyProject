package com.hdh.baekalleyproject.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityMainBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private ActivityMainBinding mBinding;
    private MainActivityContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        mBinding.setMainActivity(this);
        mPresenter = new MainActivityPresenter(this, this, this);

        initData();

        mBinding.btFilter.setOnClickListener(v->
            mPresenter.clickFilter()
        );
    }

    /**
     * 데이터 초기화 및 생성
     */
    private void initData(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(mBinding.rvRestaurantList);
    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }
}
