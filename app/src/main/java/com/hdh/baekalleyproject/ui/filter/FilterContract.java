package com.hdh.baekalleyproject.ui.filter;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface FilterContract {
    interface View extends BaseActivityContract.View {

        void changeTintColorOfFoodType(int index , boolean state);
        void changeTintColorOfPriceType(int index  , boolean state);

        void changeColorReset();
    }
    interface Presenter extends BaseActivityContract.Presenter {
        void setAlleyView(RecyclerView recyclerView);

        void clickDismiss();
        void clickReset();

        void clickFoodType(android.view.View view , int index);
        void clickPriceType(android.view.View view , int index);

        void clickSelectionComplete(TextView[] foodTypeTextViews , TextView[] priceTypeTextViews);
    }
}
