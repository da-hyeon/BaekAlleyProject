package com.hdh.baekalleyproject.ui.review_all;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.RestaurantReviewListAdapter;
import com.hdh.baekalleyproject.data.model.ReviewList;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAllPresenter extends BaseActivityPresenter implements ReviewAllContract.Presenter {

    private ReviewAllContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private UserInformation mUserInformation;

    private final int mMoveToXValue;
    private RestaurantReviewListAdapter mRestaurantReviewListAdapter;

    private int mReviewType;
    private String mRestaurantID;
    private ReviewList mReviewList;
    

    public ReviewAllPresenter(ReviewAllContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mUserInformation = MyApplication.getUserInformationInstance();

        mMoveToXValue = Math.round(23 * mContext.getResources().getDisplayMetrics().density);
        mRestaurantReviewListAdapter = new RestaurantReviewListAdapter(mContext , mActivity);
    }

    @Override
    public void setView(Intent getIntent) {

        mReviewType = getIntent.getIntExtra(Constants.REVIEW_FILTER_TYPE , -1);
        mRestaurantID = getIntent.getStringExtra(Constants.RESTAURANT_ID);

        if (mRestaurantID == null || mReviewType == -1){
            mView.showToast("해당 식당에 대한 리뷰를 불러올 수 없습니다.");
            mView.removeActivity();
        }


    }

    @Override
    public void setRecyclerView(RecyclerView rvReviewList) {
        LinearLayoutManager reviewViewerLayoutManager = new LinearLayoutManager(mContext);
        reviewViewerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvReviewList.setLayoutManager(reviewViewerLayoutManager);
        rvReviewList.setAdapter(mRestaurantReviewListAdapter);

        rvReviewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                if (lastVisibleItemPosition == itemTotalCount) {
                    Log.d("Finish", "last Position...");
                }
            }
        });
    }

    @Override
    public void loadData() {
        mView.showLoading();

        Call<ReviewList> selectRegistrationReviewFilter = MyApplication
                .getRestAdapter()
                .selectRegistrationReviewFilter(
                        mRestaurantID,
                        mUserInformation.getId(),
                        mReviewType);

        selectRegistrationReviewFilter.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(@NonNull Call<ReviewList> call, @NonNull Response<ReviewList> response) {
                if (response.isSuccessful()) {
                    mReviewList = response.body();

                    if (mReviewList != null) {

                        mRestaurantReviewListAdapter.setRestaurantReviewList(mReviewList.getReviewList());
                        mRestaurantReviewListAdapter.notifyDataSetChanged();

                        if (response.code() == 200) {
                            //로딩 숨기기
                            mView.hideLoading();
                        }

                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                        mView.removeActivity();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewList> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                Log.d("error", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void clickTasteAll(boolean onCheck) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(true);
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(false);

        mView.moveTasteBar(0);

        mReviewType = Constants.REVIEW_TASTE_ALL;
        loadData();
    }

    @Override
    public void clickTasteGreat(boolean onCheck , int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(true);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(false);

        mView.moveTasteBar(location[0] - mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_GREAT;
        loadData();
    }

    @Override
    public void clickTasteGood(boolean onCheck , int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(true);
        mView.changeTasteBadColor(false);

        mView.moveTasteBar(location[0] - mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_GOOD;
        loadData();
    }

    @Override
    public void clickTasteBad(boolean onCheck , int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(true);

        mView.moveTasteBar(location[0] - mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_BAD;
        loadData();
    }
}
