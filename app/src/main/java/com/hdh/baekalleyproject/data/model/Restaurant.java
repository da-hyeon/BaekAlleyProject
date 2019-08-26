package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {

    @SerializedName("rt_idx")
    private String restaurantID;

    @SerializedName("rt_nm")
    private String restaurantName;

    @SerializedName("mn_nm")
    private String restaurantRepFood;

    @SerializedName("ri_url")
    private String restaurantImageURL;

    @SerializedName("at_nm")
    private String restaurantArea;

    @SerializedName("cg_nm")
    private String restaurantFoodType;

    @SerializedName("rt_nov")
    private String restaurantNumberOfView;

    @SerializedName("ay_nm")
    private String restaurantAlley;

    @SerializedName("rev_nov")
    private String restaurantNumberOfReview;

    @SerializedName("rt_update")
    private String restaurantUpdate;

    @SerializedName("rt_dtl_adres")
    private String restaurantAddress;

    @SerializedName("like_nov")
    private String restaurantNumberOfLike;

    @SerializedName("avrg_Price")
    private String restaurantPriceRange;


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

    public String getRestaurantNumberOfView() {
        return restaurantNumberOfView;
    }

    public String getRestaurantAlley() {
        return restaurantAlley;
    }

    public String getRestaurantNumberOfReview() {
        return restaurantNumberOfReview;
    }

    public String getRestaurantUpdate() {
        return restaurantUpdate;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public String getRestaurantNumberOfLike() {
        return restaurantNumberOfLike;
    }

    public String getRestaurantPriceRange() {
        return restaurantPriceRange;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID='" + restaurantID + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantRepFood='" + restaurantRepFood + '\'' +
                ", restaurantImageURL='" + restaurantImageURL + '\'' +
                ", restaurantArea='" + restaurantArea + '\'' +
                ", restaurantFoodType='" + restaurantFoodType + '\'' +
                ", restaurantNumberOfView='" + restaurantNumberOfView + '\'' +
                ", restaurantAlley='" + restaurantAlley + '\'' +
                ", restaurantNumberOfReview='" + restaurantNumberOfReview + '\'' +
                ", restaurantUpdate='" + restaurantUpdate + '\'' +
                ", restaurantAddress='" + restaurantAddress + '\'' +
                ", restaurantNumberOfLike='" + restaurantNumberOfLike + '\'' +
                ", restaurantPriceRange='" + restaurantPriceRange + '\'' +
                '}';
    }
}
