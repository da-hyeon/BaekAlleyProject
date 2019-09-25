package com.hdh.baekalleyproject.ui.review_detail;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

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
        void setCommentInitialization();

        boolean isLikeMarkChecked();

        void changeLikeTextColor(int color);
        void changeLikeCountColor(int color);

        void showAnimation();

    }

    interface Presenter extends BaseActivityContract.Presenter {
        void setView(Intent getIntent);
        void setRecyclerView(RecyclerView commentView);

        void loadComment();

        void clickReviewLike();
        void clickRegistrationComment(String rvComment);
    }
}
