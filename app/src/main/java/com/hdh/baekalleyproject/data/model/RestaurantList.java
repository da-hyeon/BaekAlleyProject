package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantList implements Serializable {

    @SerializedName("list")
    private ArrayList<Restaurant> restaurantList;

    @SerializedName("maxPage")
    private int maxPage;

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public int getMaxPage() {
        return maxPage;
    }
}
