package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.adapter.RestaurantImageListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantMenuListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantReviewListAdapter;
import com.hdh.baekalleyproject.data.model.RestaurantImage;
import com.hdh.baekalleyproject.data.model.RestaurantMenu;
import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailPresenter extends BaseActivityPresenter implements RestaurantDetailContract.Presenter{
    private RestaurantDetailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    RestaurantDetailPresenter(RestaurantDetailContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void getIntent(Intent intent) {
        //전달받은 식당정보 받기
        Intent getIntent = intent;

    }

    @Override
    public double[] setMapLocation() {

        Geocoder geocoder = new Geocoder(mContext);

        //주소 입력
        String address = "서울특별시 서대문구 이화여대5길 20-6";

        double lat = 0 , lon = 0;

        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(address,10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constants.MAP_ERROR_TAG,"입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Log.e(Constants.MAP_ERROR_TAG,"해당되는 주소 정보는 없습니다");
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
            }
        }

        return new double[]{lat , lon};
    }

    @Override
    public void setView(RecyclerView rvImageView , RecyclerView rvMenuView , RecyclerView rvReviewView) {
        ArrayList<RestaurantImage> restaurantImages = new ArrayList<>();
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/26ea50b1a63a4bcead135ab00d7bc1f9.jpg"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImageView.setLayoutManager(linearLayoutManager);
        rvImageView.setAdapter(new RestaurantImageListAdapter( restaurantImages , mContext));

        ArrayList<RestaurantMenu> restaurantMenus = new ArrayList<>();
        restaurantMenus.add(new RestaurantMenu("생등심 돈카츠" , "7,000원"));
        restaurantMenus.add(new RestaurantMenu("생등심 돈카츠" , "7,000원"));
        restaurantMenus.add(new RestaurantMenu("생등심 돈카츠" , "7,000원"));
        restaurantMenus.add(new RestaurantMenu("생등심 돈카츠" , "7,000원"));
        restaurantMenus.add(new RestaurantMenu("생등심 돈카츠" , "7,000원"));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuView.setLayoutManager(linearLayoutManager1);
        rvMenuView.setAdapter(new RestaurantMenuListAdapter( restaurantMenus , mContext));

        ArrayList<Review> reviewArrayList = new ArrayList<>();
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvReviewView.setLayoutManager(linearLayoutManager2);
        rvReviewView.setAdapter(new RestaurantReviewListAdapter( reviewArrayList , mContext));
    }

    @Override
    public void clickGo() {

    }

    @Override
    public void clickWriteReview() {

    }

    @Override
    public void clickMap() {

    }

    @Override
    public void clickCall() {

    }

    @Override
    public void clickAddressCopy() {

    }

    @Override
    public void clickWrongInfo() {

    }

    @Override
    public void clickGreat() {

    }

    @Override
    public void clickGood() {

    }

    @Override
    public void clickBad() {

    }
}
