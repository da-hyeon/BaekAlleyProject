package com.hdh.baekalleyproject.ui.login;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface LoginContract {
    interface View extends BaseActivityContract.View{

    }
    interface Presenter extends BaseActivityContract.Presenter {
        void initView();
        void getHashKey();

        void clickKakaoLogin();
        void clickEmailLogin();
        void clickNoneLogin();
    }
}
