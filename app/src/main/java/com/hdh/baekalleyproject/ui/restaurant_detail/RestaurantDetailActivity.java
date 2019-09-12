package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityRestaurantDetailBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class RestaurantDetailActivity extends BaseActivity implements RestaurantDetailContract.View {

    private RestaurantDetailContract.Presenter mPresenter;
    private ActivityRestaurantDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);
        mPresenter = new RestaurantDetailPresenter(this, this, this);
        initData();

        //뒤로가기 클릭
        mBinding.vBack.setOnClickListener(v ->
                mPresenter.clickDismiss()
        );

        //공유하기 클릭
        mBinding.ivShare.setOnClickListener(v -> {
                mPresenter.clickShare();
        });

        //더보기 클릭
        mBinding.ivMore.setOnClickListener(v -> {
            mPresenter.clickMore();
        });

        //가야쥬 클릭
        mBinding.vNumberOfLikeButton.setOnClickListener(v -> {
            mPresenter.clickNumberOfLikeText(mBinding.tvNumberOfLikeButton.getCurrentTextColor() == ContextCompat.getColor(this , R.color.colorPrimary));
        });

        //리뷰쓰기 클릭
        mBinding.vWriteReview.setOnClickListener(v -> {
            mPresenter.clickWriteReview();
        });

        //맵 클릭
        mBinding.mvMap.setOnClickListener(v -> {

        });

        //전화하기 클릭
        mBinding.vCall.setOnClickListener(v -> {

        });

        //주소복사 클릭
        mBinding.vAddressCopy.setOnClickListener(v -> {

        });

        //잘못된 정보알림 클릭
        mBinding.vWrongInfo.setOnClickListener(v -> {
            mPresenter.clickWrongInfo();
        });

        //맛있네유 클릭
        mBinding.llGreat.setOnClickListener(v -> {

        });

        //괜찮네유 클릭
        mBinding.llGood.setOnClickListener(v -> {

        });

        //별론데유 클릭
        mBinding.llBad.setOnClickListener(v -> {

        });
    }

    /**
     * 데이터 생성 및 초기화
     */
    private void initData() {
        mPresenter.setRecyclerView( mBinding.rvRestaurantImageList, mBinding.rvRestaurantMenuList, mBinding.rvReviewList);

        //mPresenter.setView(getIntent() , mBinding.rvRestaurantImageList, mBinding.rvRestaurantMenuList, mBinding.rvReviewList);

        //fragment로 진행 시 밑의 부분 주석 해제
       /* MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }*/

       //mPresenter.mapAsync(mBinding.mvMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(getIntent());
        mPresenter.mapAsync(mBinding.mvMap);
    }

    @Override
    public void onBackPressed() {
        mPresenter.clickDismiss();
    }

    @Override
    public void changeNumberOfLikeText(boolean state) {
        if (state) {
            mBinding.ivNumberOfLikeButton.setImageResource(R.drawable.icon_likeit_on);
            mBinding.tvNumberOfLikeButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.ivNumberOfLikeButton.setImageResource(R.drawable.icon_likeit);
            mBinding.tvNumberOfLikeButton.setTextColor(ContextCompat.getColor(this, R.color.goTextDefaultColor));
        }
    }

    @Override
    public void setRestaurantAlley(String alley) {
        mBinding.tvAlley.setText(alley);
    }

    @Override
    public void setRestaurantName(String name) {
        mBinding.tvTitle.setText(name);
        mBinding.tvRestaurantName.setText(name);
    }

    @Override
    public void setRestaurantNumberOfView(String numberOfView) {
        mBinding.tvNumberOfView.setText(numberOfView);
    }

    @Override
    public void setRestaurantNumberOfReview(String numberOfReview) {
        mBinding.tvNumberOfReview.setText(numberOfReview);
    }

    @Override
    public void setRestaurantNumberOfLike(String numberOfLike) {
        mBinding.tvNumberOfLike.setText(numberOfLike);
    }

    @Override
    public void setRanking(String ranking) {
        mBinding.tvRating.setText(ranking);
    }

    @Override
    public void setAddress(String address) {
        mBinding.tvRestaurantAddress.setText(address);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setUpdate(String update) {
        mBinding.tvUpdate.setText("업데이트: " + update);
    }

    @Override
    public void setBusinessHours(String businessHours) {
        mBinding.tvBusinessHours.setText(businessHours);
    }

    @Override
    public void setLastOrderTime(String lastOrderTime) {
        mBinding.tvLastOrderTime.setText(lastOrderTime);
    }

    @Override
    public void setBreakTime(String breakTime) {
        mBinding.tvBreakTime.setText(breakTime);
    }

    @Override
    public void setHoliday(String holiday) {
        mBinding.tvHoliday.setText(holiday);
    }

    @Override
    public void setPrice(String price) {
        mBinding.tvPriceRange.setText(price);
    }

    @Override
    public void showBusinessHours() {
        mBinding.tvBusinessHoursText.setVisibility(View.VISIBLE);
        mBinding.tvBusinessHours.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBreakTime() {
        mBinding.tvBusinessHoursText.setVisibility(View.VISIBLE);
        mBinding.tvBusinessHours.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLastOrderTime() {
        mBinding.tvLastOrderText.setVisibility(View.VISIBLE);
        mBinding.tvLastOrderTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHoliday() {
        mBinding.tvHolidayText.setVisibility(View.VISIBLE);
        mBinding.tvHoliday.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBusinessHours() {
        mBinding.tvBusinessHoursText.setVisibility(View.GONE);
        mBinding.tvBusinessHours.setVisibility(View.GONE);
    }

    @Override
    public void hideBreakTime() {
        mBinding.tvBusinessHoursText.setVisibility(View.GONE);
        mBinding.tvBusinessHours.setVisibility(View.GONE);
    }

    @Override
    public void hideLastOrderTime() {
        mBinding.tvLastOrderText.setVisibility(View.GONE);
        mBinding.tvLastOrderTime.setVisibility(View.GONE);
    }

    @Override
    public void hideHoliday() {
        mBinding.tvHolidayText.setVisibility(View.GONE);
        mBinding.tvHoliday.setVisibility(View.GONE);
    }

    @Override
    public void changeGoColor(boolean status) {
        if (status) {
            mBinding.ivNumberOfLikeButton.setColorFilter(ContextCompat.getColor(this , R.color.colorPrimary));
            mBinding.tvNumberOfLikeButton.setTextColor(ContextCompat.getColor(this , R.color.colorPrimary));
        } else {
            mBinding.ivNumberOfLikeButton.setColorFilter(null);
        }
    }
}
