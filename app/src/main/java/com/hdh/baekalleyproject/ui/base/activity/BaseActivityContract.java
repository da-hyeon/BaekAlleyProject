package com.hdh.baekalleyproject.ui.base.activity;

import android.content.Intent;

public interface BaseActivityContract {
    interface View{
        void isPermissionCheck();

        void removeActivity();
        void moveActivity(Intent intent);

        void showToast(String content);
    }

    interface Presenter{
        void clickDismiss();
        void clickOptionDismiss();
    }
}
