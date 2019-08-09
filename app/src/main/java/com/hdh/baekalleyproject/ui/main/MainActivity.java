package com.hdh.baekalleyproject.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityMainBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;
import com.hdh.baekalleyproject.ui.myinfo.MyInfoFragment;
import com.hdh.baekalleyproject.ui.news.NewsFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantFragment;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private ActivityMainBinding mBinding;
    private MainActivityContract.Presenter mPresenter;

    private RestaurantFragment mRestaurantFragment;
    private NewsFragment mNewsFragment;
    private MyInfoFragment mMyInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        mBinding.setMainActivity(this);
        mPresenter = new MainActivityPresenter(this, this, this , getSupportFragmentManager());

        mRestaurantFragment = new RestaurantFragment();
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();

        initData();

    }

    /**
     * 데이터 초기화 및 생성
     */
    private void initData(){

        mPresenter.setData(mBinding.vpView, mBinding.tlMenu);
        //mBinding.vpPager.setAdapter(new MainTabPagerAdapter(getSupportFragmentManager()));
        //mBinding.tlMenu.setupWithViewPager(mBinding.vpPager);

    }


}
