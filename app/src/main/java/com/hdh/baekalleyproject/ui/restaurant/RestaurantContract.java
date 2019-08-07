package com.hdh.baekalleyproject.ui.restaurant;

import android.support.v7.widget.RecyclerView;

public interface RestaurantContract {
    interface View{

    }
    interface Presenter{
        void setView(RecyclerView recyclerView);

        void clickFilter();
        void clickSearch();
    }
}
