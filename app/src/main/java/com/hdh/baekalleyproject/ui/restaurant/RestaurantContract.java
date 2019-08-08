package com.hdh.baekalleyproject.ui.restaurant;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

public interface RestaurantContract {
    interface View{

    }
    interface Presenter{
        void setView(RecyclerView recyclerView , ViewPager viewPager, TabLayout tabLayout);

        void clickFilter();
        void clickSearch();
    }
}
