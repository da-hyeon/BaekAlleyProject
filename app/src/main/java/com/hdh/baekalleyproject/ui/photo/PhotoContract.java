package com.hdh.baekalleyproject.ui.photo;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface PhotoContract {
    interface View extends BaseActivityContract.View {
        void setPage(int currentPage , int pageCount);
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void setView(Intent intent , ViewPager viewPager);

    }
}
