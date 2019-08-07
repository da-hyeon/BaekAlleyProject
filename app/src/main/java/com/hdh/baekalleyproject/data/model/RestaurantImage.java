package com.hdh.baekalleyproject.data.model;

public class RestaurantImage {
    private String restaurantImageURL;

    public RestaurantImage(String restaurantImageURL) {
        this.restaurantImageURL = restaurantImageURL;
    }

    public String getRestaurantImageURL() {
        return restaurantImageURL;
    }

    public void setRestaurantImageURL(String restaurantImageURL) {
        this.restaurantImageURL = restaurantImageURL;
    }
}
