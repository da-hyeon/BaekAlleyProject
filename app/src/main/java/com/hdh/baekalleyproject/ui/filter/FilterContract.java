package com.hdh.baekalleyproject.ui.filter;

import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface FilterContract {
    interface View extends BaseActivityContract.View {

        void changeTintColorOfFoodType(android.view.View view , boolean state);
        void changeTintColorOfPriceType(android.view.View view , boolean state);
    }
    interface Presenter{
        void setAlleyView(RecyclerView recyclerView);

        void clickDismiss();

        void clickFoodType(android.view.View view);
        void clickPriceType(android.view.View view);
    }
}
