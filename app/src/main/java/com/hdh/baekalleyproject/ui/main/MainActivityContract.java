package com.hdh.baekalleyproject.ui.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

public interface MainActivityContract {
    interface View{
        void moveActivity(Intent intent);
    }
    interface Presenter{
        void setView(RecyclerView recyclerView);

        void clickFilter();
        void clickSearch();
    }
}
