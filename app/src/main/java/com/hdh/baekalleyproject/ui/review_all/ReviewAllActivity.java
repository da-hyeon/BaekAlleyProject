package com.hdh.baekalleyproject.ui.review_all;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityReviewAllBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class ReviewAllActivity extends BaseActivity implements ReviewAllContract.View {

    private ActivityReviewAllBinding mBinding;
    private ReviewAllContract.Presenter mPresenter;

    private  int[] location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review_all);
        mBinding.setReviewAllActivity(this);
        mPresenter = new ReviewAllPresenter(this, this, this);

        initData();


        mBinding.llTasteAll.setOnClickListener(v->
            mPresenter.clickTasteAll(mBinding.cbTasteAll.isChecked())
        );

        mBinding.llTasteGreat.setOnClickListener(v-> {

            mBinding.tvTasteGreat.getLocationOnScreen(location);
            mPresenter.clickTasteGreat(mBinding.cbTasteGreat.isChecked() , location);

        });

        mBinding.llTasteGood.setOnClickListener(v-> {
            mBinding.tvTasteGood.getLocationOnScreen(location);
            mPresenter.clickTasteGood(mBinding.cbTasteGood.isChecked() , location);
        });

        mBinding.llTasteBad.setOnClickListener(v-> {
            mBinding.tvTasteBad.getLocationOnScreen(location);
            mPresenter.clickTasteBad(mBinding.cbTasteBad.isChecked() , location);
        });

        mBinding.vDismiss.setOnClickListener(v->
                mPresenter.clickDismiss()
        );
    }

    private void initData() {
        mPresenter.setView(getIntent());
        mPresenter.setRecyclerView(mBinding.rvReviewList);
        mBinding.rvReviewList.setNestedScrollingEnabled(false);
        location = new int[2];

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        switch (getIntent().getIntExtra(Constants.REVIEW_FILTER_TYPE , -1)){
            case Constants.REVIEW_TASTE_GREAT:
                mBinding.tvTasteGreat.getLocationOnScreen(location);
                mPresenter.clickTasteGreat(mBinding.cbTasteGreat.isChecked() , location);
                break;
            case Constants.REVIEW_TASTE_GOOD:
                mBinding.tvTasteGood.getLocationOnScreen(location);
                mPresenter.clickTasteGood(mBinding.cbTasteGood.isChecked() , location);
                break;
            case Constants.REVIEW_TASTE_BAD:
                mBinding.tvTasteBad.getLocationOnScreen(location);
                mPresenter.clickTasteBad(mBinding.cbTasteBad.isChecked() , location);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void changeTasteAllColor(boolean status) {
        mBinding.cbTasteAll.setChecked(status);

        if (status){
            mBinding.tvTasteAll.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.tvTasteAll.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));

        }
    }

    @Override
    public void changeTasteGreatColor(boolean status) {
        mBinding.cbTasteGreat.setChecked(status);

        if (status){
            mBinding.tvTasteGreat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.tvTasteGreat.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));
        }
    }

    @Override
    public void changeTasteGoodColor(boolean status) {
        mBinding.cbTasteGood.setChecked(status);

        if (status){
            mBinding.tvTasteGood.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.tvTasteGood.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));
        }
    }

    @Override
    public void changeTasteBadColor(boolean status) {
        mBinding.cbTasteBad.setChecked(status);

        if (status){
            mBinding.tvTasteBad.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.tvTasteBad.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));
        }
    }

    @Override
    public void moveTasteBar(int dp) {
        mBinding.vTasteBar.animate().translationX(dp);
    }

    @Override
    public void showLoading() {
        mBinding.pbLoading.setVisibility(View.VISIBLE);
        mBinding.pbLoading.setIndeterminate(true);
        mBinding.rvReviewList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mBinding.pbLoading.setVisibility(View.GONE);
        mBinding.rvReviewList.setVisibility(View.VISIBLE);
    }
}
