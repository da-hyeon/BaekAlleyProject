package com.hdh.baekalleyproject.ui.review_write;

import android.app.Activity;
import android.content.Context;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

public class ReviewWritePresenter extends BaseActivityPresenter implements ReviewWriteContract.Presenter {
    private ReviewWriteContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public ReviewWritePresenter( ReviewWriteContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void clickTasteGreat() {
        mView.changeTasteGreatColor(true);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(false);
    }

    @Override
    public void clickTasteGood() {
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(true);
        mView.changeTasteBadColor(false);
    }

    @Override
    public void clickTasteBad() {
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(true);

    }
}
