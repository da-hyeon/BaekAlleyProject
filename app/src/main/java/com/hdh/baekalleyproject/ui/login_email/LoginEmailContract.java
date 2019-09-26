package com.hdh.baekalleyproject.ui.login_email;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface LoginEmailContract {
    interface View extends BaseActivityContract.View {
        void changeEmailClearButton(boolean status);
        void changePasswordClearButton(boolean status);
        void changeLoginButtonColor(boolean status);

        void changeEmailText(String text);
        void changePasswordText(String text);
    }
    interface Presenter extends BaseActivityContract.Presenter{
        void enteringEmailText(CharSequence charSequence);
        void enteringPasswordText(CharSequence charSequence);

        void clickEmailClear();
        void clickPasswordClear();

    }
}
