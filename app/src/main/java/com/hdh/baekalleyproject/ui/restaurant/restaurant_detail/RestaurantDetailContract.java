package com.hdh.baekalleyproject.ui.restaurant.restaurant_detail;

import android.support.v7.widget.RecyclerView;

public interface RestaurantDetailContract {
    interface View{

    }
    interface Presenter{
        void setView(RecyclerView recyclerView);
    }
}
