package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantPrice {
    @SerializedName("avrg_Price")
    private String price;

    public String getPrice() {
        return price;
    }
}
