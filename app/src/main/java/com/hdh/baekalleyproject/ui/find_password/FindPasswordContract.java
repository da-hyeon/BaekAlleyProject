package com.hdh.baekalleyproject.ui.find_password;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface FindPasswordContract {
    interface View extends BaseActivityContract.View {
        void changeEmailClearButton(boolean status);
        void changeSendButtonColor(boolean status);
        void changeEmailText(String text);
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void enteringEmailText(CharSequence charSequence);
        void clickEmailClear();
    }
}
