package com.hdh.baekalleyproject.ui.review_write;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewWritePresenter extends BaseActivityPresenter implements ReviewWriteContract.Presenter {
    private ReviewWriteContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private UserInformation mUserInformation;
    private int restaurantID;


    ReviewWritePresenter(ReviewWriteContract.View mView, Context mContext, Activity mActivity, Intent getIntent) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        loadUser();
        restaurantID = getIntent.getIntExtra(Constants.RESTAURANT_ID , -1);
        if (restaurantID == -1){
            mView.showToast("식당 아이디를 불러오지 못했습니다.");
            mActivity.finish();
        }
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

    /**
     * 리뷰등록 클릭 이벤트 처리
     */
    @Override
    public void clickRegistrationReview(boolean greatStatus, boolean goodStatus, boolean badStatus,
                                        String title,
                                        String menu,
                                        String content) {

        if (getType(greatStatus, goodStatus, badStatus) == null){
            mView.showToast("맛 평가를 선택해주세요.");
            return;
        } else if (title.equals("") || menu.equals("") || content.equals("")){
            mView.showToast("빈 칸을 채워주세요.");
            return;
        }
        Call<Void> requestRegistrationReview = MyApplication
                .getRestAdapter()
                .requestRegistrationReview(
                        title,
                        menu,
                        content,
                        getScore(greatStatus, goodStatus, badStatus) ,
                        getType(greatStatus, goodStatus, badStatus) ,
                        restaurantID,
                        Integer.parseInt(mUserInformation.getId()));

        requestRegistrationReview.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    mView.removeActivity();
                    mView.showToast("리뷰 등록에 성공 했습니다");
                    Log.d("review" , "리뷰 등록에 성공 했습니다.");
                } else {
                    //mView.showFailDialog("실패" , "데이터 로딩 실패");
                    mView.showToast("리뷰 등록에 실패 했습니다");
                    Log.d("실패", "데이터 로딩 실패");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                Log.d("error", t.getLocalizedMessage());
            }
        });
    }

    private String getType(boolean greatStatus, boolean goodStatus, boolean badStatus) {
        if (greatStatus)
            return "1";
        else if (goodStatus)
            return "2";
        else if (badStatus)
            return "3";
        else
            return null;
    }

    private double getScore(boolean greatStatus, boolean goodStatus, boolean badStatus) {
        if (greatStatus)
            return 5;
        else if (goodStatus)
            return 3.5;
        else if (badStatus)
            return 2;
        else
            return 0;
    }

    /**
     * 저장된 유저 정보 불러오기
     */
    private void loadUser() {
        String json = mPrefs.getString(Constants.USER_SAVE_DATA, null);
        if (json != null) {
            mUserInformation = mGson.fromJson(json, UserInformation.class);
        }
    }
}
