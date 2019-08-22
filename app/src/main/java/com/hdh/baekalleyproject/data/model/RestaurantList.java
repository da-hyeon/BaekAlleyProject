package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantList {

    @SerializedName("list")
    ArrayList<Restaurant> restaurantList;

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
