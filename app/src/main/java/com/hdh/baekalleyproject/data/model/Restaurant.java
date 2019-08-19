package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("rt_idx")
    private String restaurantID;

    @SerializedName("rt_nm")
    private String restaurantName;

    @SerializedName("RSTRN_REP_FOOD")
    private String restaurantRepFood;

    @SerializedName("ri_url")
    private String restaurantImageURL;

    @SerializedName("at_nm")
    private String restaurantArea;

    @SerializedName("cg_nm")
    private String restaurantFoodType;

    @SerializedName("rt_nov")
    private String restaurantNOV;

    @SerializedName("ay_nm")
    private String restaurantAlley;

    @SerializedName("rev_nov")
    private String restaurantReviewNOV;


    public String getRestaurantID() {
        return restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantRepFood() {
        return restaurantRepFood;
    }

    public String getRestaurantImageURL() {
        return restaurantImageURL;
    }

    public String getRestaurantArea() {
        return restaurantArea;
    }

    public String getRestaurantFoodType() {
        return restaurantFoodType;
    }

    public String getRestaurantNOV() {
        return restaurantNOV;
    }

    public String getRestaurantAlley() {
        return restaurantAlley;
    }

    public String getRestaurantReviewNOV() {
        return restaurantReviewNOV;
    }
}
