package com.hdh.baekalleyproject.ui.review_all;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface ReviewAllContract {
    interface View extends BaseActivityContract.View {
        void changeTasteAllColor(boolean status);
        void changeTasteGreatColor(boolean status);
        void changeTasteGoodColor(boolean status);
        void changeTasteBadColor(boolean status);

        void moveTasteBar(int dp);

        int getBarWidth();

        void showLoading();
        void showListLoading();

        void hideLoading();
        void hideListLoading();
    }

    interface Presenter extends BaseActivityContract.Presenter{
        void setView(Intent getIntent);
        void setRecyclerView(RecyclerView rvReviewList);
        void loadData();

        void clickTasteAll(boolean onCheck);
        void clickTasteGreat(boolean onCheck ,int[] location);
        void clickTasteGood(boolean onCheck , int[] location);
        void clickTasteBad(boolean onCheck , int[] location);
    }
}
