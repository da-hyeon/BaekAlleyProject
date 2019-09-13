package com.hdh.baekalleyproject.ui.review_detail;

import android.content.Intent;

import com.hdh.baekalleyproject.ui.base.activity.BaseActivityContract;

public interface ReviewDetailContract {
    interface View extends BaseActivityContract.View {
        void setUserImage(String userImageURL);
        void setUserName(String userName);
        void setElapsedTime(String elapsedTime);
        void setReviewMenu(String reviewMenu);
        void setTasteType(int tasteType);
        void setReviewTitle(String reviewTitle);
        void setReviewContent(String reviewContent);
        void setLikeMark(boolean status);
        void setLikeCount(int likeCount);
        void setCommentCount(int commentCount);

        boolean isLikeMarkChecked();
        void changeLikeTextColor(int color);
        void changeLikeCountColor(int color);
    }

    interface Presenter extends BaseActivityContract.Presenter {
        void setView(Intent getIntent);

        void clickReviewLike();
        void clickRegistrationComment(String comment);
    }
}
