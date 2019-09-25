package com.hdh.baekalleyproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ContentViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
//
//    public ContentViewPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return mFragmentList.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//    public void addFragment(Fragment fragment) {
//        mFragmentList.add(fragment);
//    }

    public ContentViewPagerAdapter(FragmentManager fm , List<Fragment> mFragmentList)
    {
        super(fm);
        this.mFragmentList = mFragmentList;
    }
    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                return mFragmentList.get(0);
            case 1:
                return mFragmentList.get(1);
            case 2:
                return mFragmentList.get(2);
            default:
                return null;
        }
    }
    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }


}