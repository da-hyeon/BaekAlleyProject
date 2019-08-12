package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.adapter.RestaurantImageListAdapter;
import com.hdh.baekalleyproject.data.model.RestaurantImage;

import java.util.ArrayList;

public class RestaurantDetailPresenter implements RestaurantDetailContract.Presenter{
    private RestaurantDetailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public RestaurantDetailPresenter(RestaurantDetailContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void setView(RecyclerView recyclerView) {
        ArrayList<RestaurantImage> restaurantImages = new ArrayList<>();
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));
        restaurantImages.add(new RestaurantImage("http://leehwangco.cafe24.com/resources/shopImg/87fdcaa2e23d41bfb62b68cc28aaeca4.jpg"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RestaurantImageListAdapter( restaurantImages , mContext));
    }
}
