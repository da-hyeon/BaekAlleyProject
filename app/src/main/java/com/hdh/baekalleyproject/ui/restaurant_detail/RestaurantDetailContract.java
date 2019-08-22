package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;
import com.naver.maps.map.MapView;

public interface RestaurantDetailContract {
    interface View extends BaseActivityContract.View {

        void changeGo(boolean state);

        void setRestaurantAlley(String alley);
        void setRestaurantName(String name);
        void setRestaurantNumberOfView(String numberOfView);
        void setRestaurantNumberOfReview(String numberOfReview);
        void setRestaurantNumberOfLike(String numberOfLike);
        void setRanking(String ranking);
        void setAddress(String address);
        void setUpdate(String update);
        void setBusinessHours(String businessHours);
        void setLastOrderTime(String lastOrderTime);
        void setBreakTime(String breakTime);
        void setHoliday(String holiday);
        void setPrice(String price);

        void showBusinessHours();
        void showBreakTime();
        void showLastOrderTime();
        void showHoliday();

        void hideBusinessHours();
        void hideBreakTime();
        void hideLastOrderTime();
        void hideHoliday();
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void mapAsync(MapView map);
        void setView(Intent intent , RecyclerView rvImageView , RecyclerView rvMenuView , RecyclerView rvReviewView);

        void clickShare();
        void clickMore();
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
