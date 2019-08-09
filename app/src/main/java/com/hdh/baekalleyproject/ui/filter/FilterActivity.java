package com.hdh.baekalleyproject.ui.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
        mBinding.vDismiss.setOnClickListener(v -> {
            mPresenter.clickDismiss();
        });

        //음식종류 클릭
        for (int i = 0; i < mFoodTypeViews.length; i++) {
            int index = i;
            mFoodTypeViews[i].setOnClickListener(v ->
                    mPresenter.clickFoodType(mFoodTypeViews[index])
            );
        }

        //가격대 클릭
        for (int i = 0; i < mPriceTypeViews.length; i++) {
            int index = i;
            mPriceTypeViews[i].setOnClickListener(v ->
                    mPresenter.clickPriceType(mFoodTypeViews[index])
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

    @Override
    public void changeTintColorOfFoodType(View view, boolean state) {

    }

    @Override
    public void changeTintColorOfPriceType(View view, boolean state) {

    }
}
