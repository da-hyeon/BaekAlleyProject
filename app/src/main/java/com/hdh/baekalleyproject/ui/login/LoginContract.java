package com.hdh.baekalleyproject.ui.login;

public interface LoginContract {
    interface View{

    }
    interface Presenter{
        void getHashKey();

        void clickKakaoLogin();
        void clickEmailLogin();
        void clickNoneLogin();
    }
}
