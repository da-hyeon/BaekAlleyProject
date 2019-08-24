package com.hdh.baekalleyproject.ui.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.adapter.RestaurantImageSliderAdapter;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;

public class PhotoPresenter extends BaseActivityPresenter implements PhotoContract.Presenter {

    private PhotoContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private RestaurantImageSliderAdapter mRestaurantImageSliderAdapter;
    private ArrayList<String> mRestaurantImagesArrayList;
    public PhotoPresenter(PhotoContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        mRestaurantImageSliderAdapter = new RestaurantImageSliderAdapter(mContext);
        mRestaurantImagesArrayList = new ArrayList<>();
    }

    @Override
    public void setView(Intent intent , ViewPager viewPager) {
        String[] images = intent.getStringArrayExtra(Constants.RESTAURANT_IMAGES);
        int clickItemPosition = intent.getIntExtra(Constants.RESTAURANT_IMAGE_CLICK_POSITION , 0);
        mRestaurantImagesArrayList.addAll(Arrays.asList(images));

        mRestaurantImageSliderAdapter.setmImageArrayList(mRestaurantImagesArrayList);
        viewPager.setAdapter(mRestaurantImageSliderAdapter);
        viewPager.setCurrentItem(clickItemPosition);

        mView.setPage(clickItemPosition + 1 , mRestaurantImageSliderAdapter.getCount());

        //뷰 페이저 이동
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentPage = position + 1;
                mView.setPage(currentPage , mRestaurantImageSliderAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
