package com.hdh.baekalleyproject.ui.main;

import android.app.Activity;
import android.content.Context;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;

    MainActivityPresenter(MainActivityContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

}
