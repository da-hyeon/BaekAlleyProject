package com.hdh.baekalleyproject.ui.base.activity;

import android.app.Activity;
import android.content.Context;

import com.hdh.baekalleyproject.R;

public class BaseActivityPresenter implements BaseActivityContract.Presenter {

    private BaseActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public BaseActivityPresenter(BaseActivityContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }


    @Override
    public void clickDismiss() {
        mView.removeActivity();
    }

    @Override
    public void clickOptionDismiss() {
        mView.removeActivity();
        mActivity.overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }
}
