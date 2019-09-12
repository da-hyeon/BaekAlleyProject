package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantDetail {


    @SerializedName("rev")
    private ArrayList<Review> reviewList;

    @SerializedName("rstrn")
    private ArrayList<Restaurant> restaurant;

    @SerializedName("rstrn_Img")
    private ArrayList<RestaurantImage> restaurantImageList;

    @SerializedName("bsns_Hours")
    private ArrayList<RestaurantBusinessHours> businessHoursList;

    @SerializedName("rstrn_Menu")
    private ArrayList<RestaurantMenu> restaurantMenuList;

    @SerializedName("avrg_Price")
    private ArrayList<RestaurantPrice> restaurantPrice;

    @SerializedName("rstrn_Like")
    private String WillGoClickStatus;



    public ArrayList<Restaurant> getRestaurant() {
        return restaurant;
    }

    public ArrayList<RestaurantImage> getRestaurantImageList() {
        return restaurantImageList;
    }

    public ArrayList<RestaurantBusinessHours> getBusinessHoursList() {
        return businessHoursList;
    }

    public ArrayList<RestaurantMenu> getRestaurantMenuList() {
        return restaurantMenuList;
    }

    public ArrayList<RestaurantPrice> getRestaurantPrice() {
        return restaurantPrice;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public String getWillGoClickStatus() {
        return WillGoClickStatus;
    }

}
