package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;

public class FilterPresenter implements FilterContract.Presenter {
    private FilterContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void clickDismiss() {
        mView.removeActivity();
    }
}
