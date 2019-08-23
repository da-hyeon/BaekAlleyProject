package com.hdh.baekalleyproject.ui.modify_info;

import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface ModifyInfoContract {
    interface View extends BaseActivityContract.View{

    }
    interface Presenter extends BaseActivityContract.Presenter {
        void setView(RecyclerView recyclerView);
    }
}
