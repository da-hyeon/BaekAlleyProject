package com.hdh.baekalleyproject.ui.restaurant;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.hdh.baekalleyproject.ui.base.fragment.BaseFragmentContract;

public interface RestaurantContract {
    interface View extends BaseFragmentContract.View{
        void moveActivity(Intent intent);
        void moveOptionActivity(Intent intent);
        void moveOptionActivityForResult(Intent intent  , int requestCode);
    }
    interface Presenter extends BaseFragmentContract.Presenter {
        void setRestaurantView();
        void setAdvertisementView(ViewPager viewPager, TabLayout tabLayout);

        void clickFilter();
        void clickSearch();
    }
}