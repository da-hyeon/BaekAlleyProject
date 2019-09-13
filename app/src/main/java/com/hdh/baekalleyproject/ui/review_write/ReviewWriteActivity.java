package com.hdh.baekalleyproject.ui.review_write;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityReviewWriteBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class ReviewWriteActivity extends BaseActivity implements ReviewWriteContract.View {

    private ActivityReviewWriteBinding mBinding;
    private ReviewWriteContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review_write);
        mBinding.setReviewWriteActivity(this);
        mPresenter = new ReviewWritePresenter(this, this, this , getIntent());

        initData();

        //식당정보 클릭
        mBinding.vDismiss.setOnClickListener(v ->
                mPresenter.clickDismiss()
        );

        //맛있네유 클릭
        mBinding.vTasteGreat.setOnClickListener(v ->
                mPresenter.clickTasteGreat()
        );

        //괜찮네유 클릭
        mBinding.vTasteGood.setOnClickListener(v ->
                mPresenter.clickTasteGood()
        );

        //별론데유 클릭
        mBinding.vTasteBad.setOnClickListener(v ->
                mPresenter.clickTasteBad()
        );

        //리뷰등록 클릭
        mBinding.tvRegistrationReview.setOnClickListener(v -> {
            mPresenter.clickRegistrationReview(
                    mBinding.ivTasteGreat.getColorFilter() != null,
                    mBinding.ivTasteGood.getColorFilter() != null,
                    mBinding.ivTasteBad.getColorFilter() != null,
                    mBinding.etTitle.getText().toString(),
                    mBinding.etFoodKind.getText().toString(),
                    mBinding.etContent.getText().toString());

        });

    }

    private void initData() {

    }

    @Override
    public void changeTasteGreatColor(boolean status) {
        if (status) {
            mBinding.ivTasteGreat.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            mBinding.tvTasteGreat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.ivTasteGreat.setColorFilter(null);
            mBinding.tvTasteGreat.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));
        }
    }

    @Override
    public void changeTasteGoodColor(boolean status) {
        if (status) {
            mBinding.ivTasteGood.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            mBinding.tvTasteGood.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

        } else {
            mBinding.ivTasteGood.setColorFilter(null);
            mBinding.tvTasteGood.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor));

        }
    }

    @Override
    public void changeTasteBadColor(boolean status) {
        if (status) {
            mBinding.ivTasteBad.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            mBinding.tvTasteBad.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.ivTasteBad.setColorFilter(null);
            mBinding.tvTasteBad.setTextColor(ContextCompat.getColor(this, R.color.wrongInfoTextDefaultColor)
            );
        }
    }

}
