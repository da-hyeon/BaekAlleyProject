package com.hdh.baekalleyproject.ui.search;

import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface SearchContract {
    interface View extends BaseActivityContract.View {
        void changeSearchTerm(String searchTerm);

        void changeTextPadding(int size);
        void changeSearchedFailedText(String text);
        void changeSearchText(String text);

        void showClearButton();
        void showSearchedView();
        void showSearchView();
        void showSearchFailedView();
        void showDeleteAll();

        void hideClearButton();
        void hideSearchedView();
        void hideSearchView();
        void hideSearchFailedView();
        void hideDeleteAll();
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void setRecentSearchView(RecyclerView recyclerView);

        void setRestaurantList(RecyclerView restaurantListView);

        void loadRestaurantList();
        void loadSearchedData();

        void enteringText(CharSequence charSequence);

        void clickClearButton();
        void clickDeleteAll();
    }
}
