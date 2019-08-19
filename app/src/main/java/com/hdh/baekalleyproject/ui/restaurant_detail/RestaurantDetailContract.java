package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface RestaurantDetailContract {
    interface View extends BaseActivityContract.View {

        void changeGo(boolean state);

    }
    interface Presenter extends BaseActivityContract.Presenter{
        void getIntent(Intent intent);
        double[] setMapLocation();
        void setView(RecyclerView rvImageView , RecyclerView rvMenuView , RecyclerView rvReviewView);

        void clickGo();
        void clickWriteReview();
        void clickMap();
        void clickCall();
        void clickAddressCopy();
        void clickWrongInfo();
        void clickGreat();
        void clickGood();
        void clickBad();
    }
}
