package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;
import com.naver.maps.map.MapView;

public interface RestaurantDetailContract {
    interface View extends BaseActivityContract.View {

        void changeNumberOfLikeText(boolean state);

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
        void setReviewCountOfGreat(String count);
        void setReviewCountOfGood(String count);
        void setReviewCountOfBad(String count);

        void showBusinessHours();
        void showBreakTime();
        void showLastOrderTime();
        void showHoliday();

        void hideBusinessHours();
        void hideBreakTime();
        void hideLastOrderTime();
        void hideHoliday();

        void changeGoColor(boolean status);
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void mapAsync(MapView map);
        void setRecyclerView( RecyclerView rvImageView , RecyclerView rvMenuView , RecyclerView rvReviewView);
        void setView(Intent intent );

        void clickShare();
        void clickMore();
        void clickNumberOfLikeText(boolean state);
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
