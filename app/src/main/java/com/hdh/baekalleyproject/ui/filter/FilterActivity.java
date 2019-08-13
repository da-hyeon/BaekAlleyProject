package com.hdh.baekalleyproject.ui.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityFilterBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class FilterActivity extends BaseActivity implements FilterContract.View {

    private FilterContract.Presenter mPresenter;
    private ActivityFilterBinding mBinding;

    private View[] mFoodTypeViews;
    private View[] mPriceTypeViews;

    private View[] mFoodDotsViews;
    private View[] mPriceDotsViews;

    private ImageView[] mFoodTypeImageViews;
    private ImageView[] mPriceTypeImageViews;

    private TextView[] mFoodTypeTextViews;
    private TextView[] mPriceTypeTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        mBinding.setFilterActivity(this);

        initData();

        //닫기 버튼 클릭
        mBinding.vDismiss.setOnClickListener(v ->
            mPresenter.clickOptionDismiss()
        );

        mBinding.vReset.setOnClickListener(v ->
            mPresenter.clickReset()
        );

        //음식종류 클릭
        for (int i = 0; i < mFoodTypeViews.length; i++) {
            int index = i;
            mFoodTypeViews[i].setOnClickListener(v ->
                    mPresenter.clickFoodType(mFoodTypeViews[index], index)
            );
        }

        //가격대 클릭
        for (int i = 0; i < mPriceTypeViews.length; i++) {
            int index = i;
            mPriceTypeViews[i].setOnClickListener(v ->
                    mPresenter.clickPriceType(mPriceTypeViews[index], index)
            );
        }
    }

    private void initData() {
        mPresenter = new FilterPresenter(this, this, this);

        mPresenter.setAlleyView(mBinding.rvAlley);


        //Views
        mFoodTypeViews = new View[]{
                mBinding.vKoreanFood,
                mBinding.vChineseFood,
                mBinding.vJapaneseFood,
                mBinding.vWesternFood,
                mBinding.vSnackFood,
                mBinding.vEtcFood
        };
        mPriceTypeViews = new View[]{
                mBinding.vBelow10000,
                mBinding.vAbove10000,
                mBinding.vAbove20000,
                mBinding.vAbove30000
        };

        mFoodDotsViews = new View[]{
                mBinding.vFoodDot1,
                mBinding.vFoodDot2,
                mBinding.vFoodDot3,
                mBinding.vFoodDot4,
                mBinding.vFoodDot5,
                mBinding.vFoodDot6
        };

        mPriceDotsViews = new View[]{
                mBinding.vPriceDot1,
                mBinding.vPriceDot2,
                mBinding.vPriceDot3,
                mBinding.vPriceDot4
        };

        //ImageViews
        mFoodTypeImageViews = new ImageView[]{
                mBinding.ivKoreanFood,
                mBinding.ivChineseFood,
                mBinding.ivJapaneseFood,
                mBinding.ivWesternFood,
                mBinding.ivSnackFood,
                mBinding.ivEtcFood
        };
        mPriceTypeImageViews = new ImageView[]{
                mBinding.ivBelow10000,
                mBinding.ivAbove10000,
                mBinding.ivAbove20000,
                mBinding.ivAbove30000
        };

        //TextViews
        mFoodTypeTextViews = new TextView[]{
                mBinding.tvKoreanFood,
                mBinding.tvChineseFood,
                mBinding.tvJapaneseFood,
                mBinding.tvWesternFood,
                mBinding.tvSnackFood,
                mBinding.tvEtcFood
        };
        mPriceTypeTextViews = new TextView[]{
                mBinding.tvBelow10000,
                mBinding.tvAbove10000,
                mBinding.tvAbove20000,
                mBinding.tvAbove30000
        };
    }

    /**
     * 음식종류 색상 변경하기
     * @param index 변경할 배열의 index
     * @param state true - 선택 , false - 선택안함
     */
    @Override
    public void changeTintColorOfFoodType(int index, boolean state) {
        if (state) {
            mFoodTypeViews[index].setBackground(ContextCompat.getDrawable(this, R.drawable.round_stroke_ff4f4f));
            mFoodDotsViews[index].setVisibility(View.VISIBLE);
            mFoodTypeImageViews[index].setColorFilter(getResources().getColor(R.color.colorPrimary));
            mFoodTypeTextViews[index].setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mFoodTypeViews[index].setBackground(ContextCompat.getDrawable(this, R.drawable.round_stroke_bbbbbb));
            mFoodTypeImageViews[index].setColorFilter(getResources().getColor(R.color.foodTextDefaultColor));
            mFoodTypeTextViews[index].setTextColor(getResources().getColor(R.color.foodTextDefaultColor));
            mFoodDotsViews[index].setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 가격대 색상 변경하기
     * @param index 변경할 배열의 index
     * @param state true - 선택 , false - 선택안함
     */
    @Override
    public void changeTintColorOfPriceType(int index, boolean state) {
        if (state) {
            mPriceTypeViews[index].setBackground(ContextCompat.getDrawable(this, R.drawable.circle_shape_ff4f4f));
            mPriceDotsViews[index].setVisibility(View.VISIBLE);
            mPriceTypeImageViews[index].setColorFilter(getResources().getColor(R.color.colorPrimary));
            mPriceTypeTextViews[index].setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mPriceTypeViews[index].setBackground(ContextCompat.getDrawable(this, R.drawable.circle_shape_bbbbbb));
            mPriceDotsViews[index].setVisibility(View.INVISIBLE);
            mPriceTypeImageViews[index].setColorFilter(getResources().getColor(R.color.foodTextDefaultColor));
            mPriceTypeTextViews[index].setTextColor(getResources().getColor(R.color.priceTextDefaultColor));
        }
    }

    /**
     * 선택 초기화
     */
    @Override
    public void changeColorReset() {
        for(int i = 0; i < mFoodTypeViews.length; i++){
            if (!mFoodTypeViews[i].getTag().equals("0")) {
                mFoodTypeViews[i].setTag("0");
                changeTintColorOfFoodType(i, false);
            }
        }

        for(int i = 0; i < mPriceTypeViews.length; i++) {
            if (!mPriceTypeViews[i].getTag().equals("0")) {
                mPriceTypeViews[i].setTag("0");
                changeTintColorOfPriceType(i, false);
            }
        }
    }

    /**
     * 뒤로가기 버튼 클릭
     */
    @Override
    public void onBackPressed() {
        mPresenter.clickOptionDismiss();
    }
}
