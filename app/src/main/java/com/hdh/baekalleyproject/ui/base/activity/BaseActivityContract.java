package com.hdh.baekalleyproject.ui.base.activity;

import android.content.Intent;

public interface BaseActivityContract {
    interface View{
        void removeActivity();
        void moveActivity(Intent intent);
    }

    interface Presenter{
        void clickDismiss();
        void clickOptionDismiss();
    }
}
