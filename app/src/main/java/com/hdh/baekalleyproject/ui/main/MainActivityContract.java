package com.hdh.baekalleyproject.ui.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public interface MainActivityContract {
    interface View{
        void moveActivity(Intent intent);
    }
    interface Presenter{
        void setData(ViewPager viewpager , TabLayout tabLayout);
    }
}
