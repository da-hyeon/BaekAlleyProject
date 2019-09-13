package com.hdh.baekalleyproject.ui.review_detail;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityReviewDetailBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class ReviewDetailActivity extends BaseActivity implements ReviewDetailContract.View{

    private ActivityReviewDetailBinding mBinding;
    private ReviewDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review_detail);
        mBinding.setReviewDetailActivity(this);
        mPresenter = new ReviewDetailPresenter(this, this, this);

        initData();

        //리뷰 좋아요 클릭
        mBinding.icReview.vReviewLike.setOnClickListener(v->
                mPresenter.clickReviewLike()
        );

        //식당정보 클릭
        mBinding.vDismiss.setOnClickListener(v->
                mPresenter.clickDismiss()
        );

        mBinding.tvRegistrationComment.setOnClickListener(v->
                mPresenter.clickRegistrationComment(mBinding.tvRegistrationComment.getText().toString())
        );
    }

    private void initData() {
        mPresenter.setView(getIntent());
    }

    @Override
    public void setUserImage(String userImageURL) {
        final int IMG_SIZE  = Math.round(36 * getResources().getDisplayMetrics().density);

        if (userImageURL == null) {

        } else {
            Glide.with(this)
                    .load(userImageURL)
                    .apply(new RequestOptions().override(IMG_SIZE, IMG_SIZE).centerCrop().circleCrop())
                    .into(mBinding.icReview.ivUserImage);
        }
    }

    @Override
    public void setUserName(String userName) {
        mBinding.icReview.tvUserName.setText(userName);
    }

    @Override
    public void setElapsedTime(String elapsedTime) {
        mBinding.icReview.tvElapsedTime.setText(elapsedTime);
    }

    @Override
    public void setReviewMenu(String reviewMenu) {
        mBinding.icReview.tvReviewMenu.setText(reviewMenu);
        mBinding.icReview.tvReviewMenu.setSelected(true);
    }

    @Override
    public void setTasteType(int tasteType) {
        switch (tasteType) {
            case 1:
                mBinding.icReview.ivTaste.setImageResource(R.drawable.icon_great);
                break;
            case 2:
                mBinding.icReview.ivTaste.setImageResource(R.drawable.icon_good);
                break;
            case 3:
                mBinding.icReview.ivTaste.setImageResource(R.drawable.icon_bad);
                break;
        }
    }

    @Override
    public void setReviewTitle(String reviewTitle) {
        mBinding.icReview.tvReviewTitle.setText(reviewTitle);
    }

    @Override
    public void setReviewContent(String reviewContent) {
        mBinding.icReview.tvReviewContent.setText(reviewContent);
    }

    @Override
    public void setLikeMark(boolean status) {
        mBinding.icReview.cbLikeMark.setChecked(status);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setLikeCount(int likeCount) {
        if (likeCount > 0) {
            mBinding.icReview.tvLikeCount.setText(likeCount + "개");
        } else {
            mBinding.icReview.tvLikeCount.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setCommentCount(int commentCount) {
        if (commentCount > 0){
            mBinding.icReview.tvComment.setText("댓글 " + commentCount+"개");
        } else {
            mBinding.icReview.tvComment.setText("댓글달기");
        }
    }

    @Override
    public boolean isLikeMarkChecked() {
        return mBinding.icReview.cbLikeMark.isChecked();
    }

    @Override
    public void changeLikeTextColor(int color) {
        mBinding.icReview.tvLikeText.setTextColor(color);
    }

    @Override
    public void changeLikeCountColor(int color) {
        mBinding.icReview.tvLikeCount.setTextColor(color);
    }

}
