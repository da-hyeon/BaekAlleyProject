package com.hdh.baekalleyproject.ui.review_detail;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityReviewDetailBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class ReviewDetailActivity extends BaseActivity implements ReviewDetailContract.View {

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
        mBinding.vReviewLike.setOnClickListener(v ->
                mPresenter.clickReviewLike()
        );

        //식당정보 클릭
        mBinding.vDismiss.setOnClickListener(v ->
                mPresenter.clickDismiss()
        );

        mBinding.tvRegistrationComment.setOnClickListener(v ->
                mPresenter.clickRegistrationComment(mBinding.etComment.getText().toString())
        );

        mBinding.avLikeAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mBinding.avLikeAnimation.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void initData() {
        mPresenter.setView(getIntent());
        mPresenter.setRecyclerView(mBinding.rvReviewComment);

        mBinding.tvReviewContent.setMaxLines(Integer.MAX_VALUE);
        mBinding.tvReviewContent.setEllipsize(null);
        mBinding.rvReviewComment.setNestedScrollingEnabled(false);
       // android:maxLines="3"
       // android:ellipsize="end"
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadComment();
    }

    @Override
    public void setUserImage(String userImageURL) {
        final int IMG_SIZE = Math.round(36 * getResources().getDisplayMetrics().density);

        if (userImageURL == null) {

        } else {
            Glide.with(this)
                    .load(userImageURL)
                    .apply(new RequestOptions().override(IMG_SIZE, IMG_SIZE).centerCrop().circleCrop())
                    .into(mBinding.ivUserImage);
        }
    }

    @Override
    public void setUserName(String userName) {
        mBinding.tvUserName.setText(userName);
    }

    @Override
    public void setElapsedTime(String elapsedTime) {
        mBinding.tvElapsedTime.setText(elapsedTime);
    }

    @Override
    public void setReviewMenu(String reviewMenu) {
        mBinding.tvReviewMenu.setText(reviewMenu);
        mBinding.tvReviewMenu.setSelected(true);
    }

    @Override
    public void setTasteType(int tasteType) {
        switch (tasteType) {
            case 1:
                mBinding.ivTaste.setImageResource(R.drawable.icon_great);
                break;
            case 2:
                mBinding.ivTaste.setImageResource(R.drawable.icon_good);
                break;
            case 3:
                mBinding.ivTaste.setImageResource(R.drawable.icon_bad);
                break;
        }
    }

    @Override
    public void setReviewTitle(String reviewTitle) {
        mBinding.tvReviewTitle.setText(reviewTitle);
    }

    @Override
    public void setReviewContent(String reviewContent) {
        mBinding.tvReviewContent.setText(reviewContent);
    }

    @Override
    public void setLikeMark(boolean status) {
        mBinding.cbLikeMark.setChecked(status);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setLikeCount(int likeCount) {
        if (likeCount > 0) {
            mBinding.tvLikeCount.setText(likeCount + "개");
        } else {
            mBinding.tvLikeCount.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setCommentCount(int commentCount) {
        mBinding.tvComment.setText("댓글 " + commentCount + "개");
    }

    @Override
    public void setCommentInitialization() {
        mBinding.etComment.setText("");
    }

    @Override
    public boolean isLikeMarkChecked() {
        return mBinding.cbLikeMark.isChecked();
    }

    @Override
    public void changeLikeTextColor(int color) {
        mBinding.tvLikeText.setTextColor(color);
    }

    @Override
    public void changeLikeCountColor(int color) {
        mBinding.tvLikeCount.setTextColor(color);
    }

    @Override
    public void showAnimation() {
        mBinding.avLikeAnimation.setVisibility(View.VISIBLE);
        mBinding.avLikeAnimation.setAnimation("heart.json");
        mBinding.avLikeAnimation.playAnimation();
    }
}
