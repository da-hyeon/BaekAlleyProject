package com.hdh.baekalleyproject.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

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
        mPresenter = new MainActivityPresenter(this, this, this);

        initData();

    }

    /**
     * 데이터 초기화 및 생성
     */
    private void initData(){

        mRestaurantFragment = new RestaurantFragment();
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();

//        mBinding.vpPager.setAdapter(new MainTabPagerAdapter(getSupportFragmentManager()));
//        mBinding.tlMenu.setupWithViewPager(mBinding.vpPager);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flView, mRestaurantFragment).commitAllowingStateLoss();

        mBinding.bnMenu.setOnNavigationItemSelectedListener(new ItemSelectedListener());


    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.menuRestaurant:
                    transaction.replace(R.id.flView, mRestaurantFragment).commitAllowingStateLoss();
                    break;

                case R.id.menuNews:
                    transaction.replace(R.id.flView, mNewsFragment).commitAllowingStateLoss();
                    break;

                case R.id.menuMyInfo:
                    transaction.replace(R.id.flView, mMyInfoFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}
