package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("RSTRN_CD")
    private String restaurantID;

    @SerializedName("RSTRN_NM")
    private String restaurantName;

    @SerializedName("RSTRN_HOURS")
    private String restaurantTime;

    @SerializedName("RSTRN_REP_FOOD")
    private String restaurantRepFood;

    @SerializedName("RSTRN_IMG_URL")
    private String restaurantImageURL;

    @SerializedName("RSTRN_VALID")
    private String restaurantValid;


    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantTime() {
        return restaurantTime;
    }

    public void setRestaurantTime(String restaurantTime) {
        this.restaurantTime = restaurantTime;
    }

    public String getRestaurantRepFood() {
        return restaurantRepFood;
    }

    public void setRestaurantRepFood(String restaurantRepFood) {
        this.restaurantRepFood = restaurantRepFood;
    }

    public String getRestaurantImageURL() {
        return restaurantImageURL;
    }

    public void setRestaurantImageURL(String restaurantImageURL) {
        this.restaurantImageURL = restaurantImageURL;
    }

    public String getRestaurantValid() {
        return restaurantValid;
    }

    public void setRestaurantValid(String restaurantValid) {
        this.restaurantValid = restaurantValid;
    }
}
