package com.hdh.baekalleyproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hdh.baekalleyproject.ui.myinfo.MyInfoFragment;
import com.hdh.baekalleyproject.ui.news.NewsFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantFragment;

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private String[] fragmentTitleList = {"식당","뉴스","내정보"};
    private FragmentManager mFragmentManager;

    private RestaurantFragment mRestaurantFragment;
    private NewsFragment mNewsFragment;
    private MyInfoFragment mMyInfoFragment;

    public MainTabPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;

        mRestaurantFragment = new RestaurantFragment();
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mRestaurantFragment;
            case 1:
                return mNewsFragment;
            case 2:
                return mMyInfoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentTitleList != null ? fragmentTitleList.length : 0;
    }

    @Override
        public CharSequence getPageTitle(int position) {
        return fragmentTitleList[position];
    }
}
