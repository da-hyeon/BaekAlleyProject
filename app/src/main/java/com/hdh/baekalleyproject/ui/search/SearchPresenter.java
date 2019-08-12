package com.hdh.baekalleyproject.ui.search;

import android.app.Activity;
import android.content.Context;

public class SearchPresenter implements SearchContract.Presenter{
    private SearchContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public SearchPresenter(SearchContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }
}
