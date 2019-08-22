package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantMenu {

    @SerializedName("mn_price")
    private String menuPrice;

    @SerializedName("mn_idx")
    private String menuID;

    @SerializedName("rt_idx")
    private String restaurantID;

    @SerializedName("mn_order")
    private String menuOrder;

    @SerializedName("mn_nm")
    private String menuName;

    public String getMenuPrice() {
        return menuPrice;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public String getMenuName() {
        return menuName;
    }
}
