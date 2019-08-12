package com.hdh.baekalleyproject.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.ContentViewPagerAdapter;
import com.hdh.baekalleyproject.ui.myinfo.MyInfoFragment;
import com.hdh.baekalleyproject.ui.news.NewsFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantFragment;

import java.util.Objects;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;
    private FragmentManager mFragmentManager;

    private RestaurantFragment mRestaurantFragment;
    private NewsFragment mNewsFragment;
    private MyInfoFragment mMyInfoFragment;

    MainActivityPresenter(MainActivityContract.View mView, Context mContext, Activity mActivity , FragmentManager mFragmentManager) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;

        mRestaurantFragment = new RestaurantFragment();
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();
    }

    @Override
    public void setData(ViewPager viewpager , TabLayout tabLayout) {
        ContentViewPagerAdapter adapter = new ContentViewPagerAdapter(mFragmentManager);
        adapter.addFragment(mRestaurantFragment);
        adapter.addFragment(mNewsFragment);
        adapter.addFragment(mMyInfoFragment);
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

        View restaurantFind = mActivity.getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView restaurantFindImg = restaurantFind.findViewById(R.id.img_tab);
        TextView restaurantFindText = restaurantFind.findViewById(R.id.txt_tab);
        restaurantFindImg.setImageResource(R.drawable.selector_findshop);
        restaurantFindText.setText("식당찾기");
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(restaurantFind);

        View restaurantNews = mActivity.getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView restaurantNewsImg = restaurantNews.findViewById(R.id.img_tab);
        TextView restaurantNewsText = restaurantNews.findViewById(R.id.txt_tab);
        restaurantNewsImg.setImageResource(R.drawable.selector_shopnews);
        restaurantNewsText.setText("식당소식");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(restaurantNews);

        View myInfo = mActivity.getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView myInfoImg = myInfo.findViewById(R.id.img_tab);
        TextView myInfoText = myInfo.findViewById(R.id.txt_tab);
        myInfoImg.setImageResource(R.drawable.selector_myinfo);
        myInfoText.setText("내 정보");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(myInfo);

    }
}
