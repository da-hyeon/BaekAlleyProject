package com.hdh.baekalleyproject.ui.restaurant;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

public interface RestaurantContract {
    interface View{
        void moveActivity(Intent intent);
        void moveOptionActivity(Intent intent);
        void moveOptionActivityForResult(Intent intent  , int requestCode);
    }
    interface Presenter{
        void setView(RecyclerView recyclerView , ViewPager viewPager, TabLayout tabLayout);

        void setRestaurantFilterList(Intent intent);
        void clickFilter();
        void clickSearch();
    }
}