package com.hdh.baekalleyproject.ui.review_write;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface ReviewWriteContract {
    interface View extends BaseActivityContract.View {
        void changeTasteGreatColor(boolean status);
        void changeTasteGoodColor(boolean status);
        void changeTasteBadColor(boolean status);
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void clickTasteGreat();
        void clickTasteGood();
        void clickTasteBad();
    }
}
