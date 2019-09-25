package com.hdh.baekalleyproject.ui.review_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.RestaurantReviewCommentListAdapter;
import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.data.model.ReviewCommentList;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDetailPresenter extends BaseActivityPresenter implements ReviewDetailContract.Presenter {

    private ReviewDetailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private Review mReview;

    private UserInformation mUserInformation;
    private RestaurantReviewCommentListAdapter mRestaurantReviewCommentListAdapter;
    private ReviewCommentList mReviewCommentList;

    public ReviewDetailPresenter(ReviewDetailContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mUserInformation = MyApplication.getUserInformationInstance();
        mRestaurantReviewCommentListAdapter = new RestaurantReviewCommentListAdapter(mContext, mActivity);
    }

    @Override
    public void setView(Intent getIntent) {
        mReview = (Review) getIntent.getSerializableExtra(Constants.REVIEW_DATA);

        if (mReview != null) {
            mRestaurantReviewCommentListAdapter.setRestaurantID(mReview.getRestaurantID());
            mRestaurantReviewCommentListAdapter.setReviewID(mReview.getReviewID());
            mView.setUserImage(mReview.getUserImageURL());
            mView.setUserName(mReview.getUserName());
            mView.setElapsedTime(getElapsedTime(mReview.getReviewRegistrationDate()));
            mView.setReviewMenu(mReview.getReviewMenu());
            mView.setTasteType(mReview.getRevivewTasteType());
            mView.setReviewTitle(mReview.getReviewTitle());
            mView.setReviewContent(mReview.getReviewContent());
            mView.setLikeMark(mReview.isLikeClickStatus());
            if (mView.isLikeMarkChecked()) {
                mView.changeLikeTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                mView.changeLikeCountColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
            } else {
                mView.changeLikeTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                mView.changeLikeCountColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
            }
            mView.setLikeCount(mReview.getReviewLikeCount());
            mView.setCommentCount(mReview.getCommentCount());
        } else {
            mView.showToast("해당 리뷰 정보를 가져오지 못했습니다.");
            mView.removeActivity();
        }
    }

    @Override
    public void setRecyclerView(RecyclerView commentView) {


        // mRestaurantReviewCommentListAdapter.setRestaurantReviewCommentList(mCommentList);

        LinearLayoutManager commentViewerLayoutManager = new LinearLayoutManager(mContext);
        commentViewerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentView.setLayoutManager(commentViewerLayoutManager);
        commentView.setAdapter(mRestaurantReviewCommentListAdapter);

    }

    @Override
    public void loadComment() {
        Call<ReviewCommentList> selectRegistrationReviewComment = MyApplication
                .getRestAdapter()
                .selectRegistrationReviewComment(
                        mUserInformation.getId(),
                        mReview.getReviewID(),
                        mReview.getRestaurantID());

        selectRegistrationReviewComment.enqueue(new Callback<ReviewCommentList>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ReviewCommentList> call, @NonNull Response<ReviewCommentList> response) {

                if (response.body() != null) {
                    mReviewCommentList = response.body();

                    if (response.code() == 200) {
                        mRestaurantReviewCommentListAdapter.setRestaurantReviewCommentList(mReviewCommentList.getReviewCommentList());
                        mRestaurantReviewCommentListAdapter.notifyDataSetChanged();
                    }

                } else {
                    //mView.showFailDialog("실패" , "데이터 로딩 실패");
                    Log.d("실패", "데이터 로딩 실패");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewCommentList> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                Log.d("error", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void clickReviewLike() {
        Log.d("클릭", "진입");

        if (mUserInformation != null) {
            Log.d("진입", "진입");
            Call<Integer> requestRegistrationReviewLike = MyApplication
                    .getRestAdapter()
                    .requestRegistrationReviewLike(
                            mReview.getRestaurantID(),
                            mUserInformation.getId(),
                            mReview.getReviewID());

            requestRegistrationReviewLike.enqueue(new Callback<Integer>() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                    if (response.code() == 200) {

                        mView.setLikeMark(!mView.isLikeMarkChecked());
                        if (mView.isLikeMarkChecked()) {
                            mView.changeLikeTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                            mView.changeLikeCountColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                            mView.showAnimation();
                        } else {
                            mView.changeLikeTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                            mView.changeLikeCountColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                        }
                        mView.setLikeCount(response.body());
                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                    Log.d("error", t.getLocalizedMessage());
                }
            });

        } else {
            mView.moveLogin();
        }
    }

    @Override
    public void clickRegistrationComment(String comment) {
        if (mUserInformation != null) {
            if (comment != null && !comment.equals("")) {
                Log.d("진입", "진입");
                Call<Void> requestRegistrationReviewComment = MyApplication
                        .getRestAdapter()
                        .requestRegistrationReviewComment(
                                comment,
                                mUserInformation.getId(),
                                mReview.getReviewID(),
                                mReview.getRestaurantID());

                requestRegistrationReviewComment.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            loadComment();
                            if (response.code() == 200) {
                                mView.showToast("댓글이 등록되었습니다.");
                                mView.setCommentInitialization();
                                mView.setCommentCount(mReviewCommentList.getReviewCommentList().size()+1);
                            }
                        } else {
                            //mView.showFailDialog("실패" , comment);
                            Log.d("실패", "데이터 로딩 실패");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d("error", t.getMessage());
                        Log.d("error", t.getLocalizedMessage());
                    }
                });


            } else {
                mView.showToast("빈칸을 채워주세요.");
            }
        } else {
            mView.moveLogin();
        }
    }

    /**
     * 날짜 차이 계산
     *
     * @param date 등록된 날짜
     * @return 날짜 차이
     */
    private String getElapsedTime(String date) {
        String elapsedTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();
        String time = formatter.format(currentTime);
        Date registrationDate = null;

        try {
            registrationDate = formatter.parse(date);
            currentTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
        long diff = 0;
        if (registrationDate != null) {
            diff = registrationDate.getTime() - currentTime.getTime();
        } else {
            return "error";
        }
        long diffDays = diff / (60 * 1000);

        diffDays *= -1;
        if (diffDays == 0) {
            elapsedTime = "방금 전";
        } else if (diffDays < 60) {
            elapsedTime = diffDays + "분 전";
        } else if (diffDays < 1440) {
            elapsedTime = (diffDays / 60) + "시간 전";
        } else if (diffDays < 43200) {
            elapsedTime = (diffDays / 1440) + "일 전";
        } else if (diffDays < 518400) {
            elapsedTime = (diffDays / 43200) + "달 전";
        } else {
            elapsedTime = (diffDays / 518400) + "년 전";
        }

        return elapsedTime;
    }
}
