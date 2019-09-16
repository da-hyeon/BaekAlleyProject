package com.hdh.baekalleyproject.ui.main;

import android.support.design.widget.BottomNavigationView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface MainActivityContract {
    interface View extends BaseActivityContract.View{

    }
    interface Presenter extends BaseActivityContract.Presenter {
        void disableShiftMode(BottomNavigationView view);
       // void setData(ViewPager viewpager , TabLayout tabLayout);

        void popRestaurantFragment();
        void popNewsFragment();
        void popMyInfoFragment();

        void onBackPressed();
    }
}
