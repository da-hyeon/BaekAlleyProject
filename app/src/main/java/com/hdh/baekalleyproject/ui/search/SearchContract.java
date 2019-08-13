package com.hdh.baekalleyproject.ui.search;

import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface SearchContract {
    interface View extends BaseActivityContract.View {
        void inputInitialization();

        void changeTextPadding(int size);

        void showClearButton();

        void hideClearButton();
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void setView(RecyclerView recyclerView);
        void enteringText(CharSequence charSequence);

        void clickClearButton();
    }
}
