package com.hdh.baekalleyproject.ui.restaurant.restaurant_detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityRestaurantDetailBinding;

public class RestaurantDetailActivity extends AppCompatActivity implements RestaurantDetailContract.View{

    private RestaurantDetailContract.Presenter mPresenter;
    private ActivityRestaurantDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_restaurant_detail);
        mPresenter = new RestaurantDetailPresenter(this, this, this);
        initData();

    }

    /**
     * 데이터 생성 및 초기화
     */
    private void initData(){
        mPresenter.setView(mBinding.rvRestaurantImageList);
    }
}
