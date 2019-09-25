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
import com.hdh.baekalleyproject.data.util.ItemDecoration;
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

    private int mCurrentPage;
    private ItemDecoration mItemDecoration;


    public ReviewAllPresenter(ReviewAllContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mUserInformation = MyApplication.getUserInformationInstance();
        mCurrentPage = 1;

        mMoveToXValue = Math.round(16 * mContext.getResources().getDisplayMetrics().density);
        mRestaurantReviewListAdapter = new RestaurantReviewListAdapter(mContext, mActivity);

        mItemDecoration = new ItemDecoration(mContext , Constants.ITEM_TYPE_REVIEW);
    }

    @Override
    public void setView(Intent getIntent) {

        mReviewType = getIntent.getIntExtra(Constants.REVIEW_FILTER_TYPE, -1);
        mRestaurantID = getIntent.getStringExtra(Constants.RESTAURANT_ID);

        if (mRestaurantID == null || mReviewType == -1) {
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
        rvReviewList.addItemDecoration(mItemDecoration);

        rvReviewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;

                if (lastVisibleItemPosition == itemTotalCount) {
                    mCurrentPage++;
                    if (mCurrentPage <= mReviewList.getMaxPage()) {
                        mView.showListLoading();

                        Call<ReviewList> selectRegistrationReviewFilter = MyApplication
                                .getRestAdapter()
                                .selectRegistrationReviewFilter(
                                        mRestaurantID,
                                        mUserInformation.getId(),
                                        mReviewType,
                                        mCurrentPage);

                        selectRegistrationReviewFilter.enqueue(new Callback<ReviewList>() {
                            @Override
                            public void onResponse(@NonNull Call<ReviewList> call, @NonNull Response<ReviewList> response) {
                                if (response.isSuccessful()) {

                                    if (response.body() != null) {
                                        mReviewList.getReviewList().addAll(response.body().getReviewList());
                                        mRestaurantReviewListAdapter.notifyDataSetChanged();

                                        if (response.code() == 200) {
                                            mView.hideListLoading();
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
                    if (mCurrentPage >= mReviewList.getMaxPage()) {
                        mItemDecoration.setLastPageCheck(true);
                    }
                }
            }
        });
    }

    @Override
    public void loadData() {
        mView.showLoading();
        mItemDecoration.setLastPageCheck(false);
        mCurrentPage = 1;
        Call<ReviewList> selectRegistrationReviewFilter = MyApplication
                .getRestAdapter()
                .selectRegistrationReviewFilter(
                        mRestaurantID,
                        mUserInformation.getId(),
                        mReviewType,
                        1);

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
                            if (mCurrentPage >= mReviewList.getMaxPage()) {
                                mItemDecoration.setLastPageCheck(true);
                            }
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

        mView.moveTasteBar(mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_ALL;
        loadData();
    }

    @Override
    public void clickTasteGreat(boolean onCheck, int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(true);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(false);

        mView.moveTasteBar((mView.getBarWidth() / 4) + mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_GREAT;
        loadData();
    }

    @Override
    public void clickTasteGood(boolean onCheck, int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(true);
        mView.changeTasteBadColor(false);

        mView.moveTasteBar((mView.getBarWidth() / 4 * 2) + mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_GOOD;
        loadData();
    }

    @Override
    public void clickTasteBad(boolean onCheck, int[] location) {
        if (onCheck)
            return;

        mView.changeTasteAllColor(false);
        mView.changeTasteGreatColor(false);
        mView.changeTasteGoodColor(false);
        mView.changeTasteBadColor(true);

        mView.moveTasteBar((mView.getBarWidth() / 4 * 3) + mMoveToXValue);

        mReviewType = Constants.REVIEW_TASTE_BAD;
        loadData();
    }
}
