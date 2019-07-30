package com.hdh.baekalleyproject.ui.filter;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface FilterContract {
    interface View extends BaseActivityContract.View {

    }
    interface Presenter{
        void clickDismiss();
    }
}
