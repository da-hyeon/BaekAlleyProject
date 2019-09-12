package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantList implements Serializable {

    @SerializedName("list")
    private ArrayList<Restaurant> restaurantList;

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}
