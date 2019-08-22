package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantBusinessHours {
    @SerializedName("tm_type")
    private String businessHoursType;

    @SerializedName("tm_time")
    private String businessHours;

    @SerializedName("rt_idx")
    private String restaurantID;

    @SerializedName("tm_idx")
    private String businessHoursID;

    public String getBusinessHoursType() {
        return businessHoursType;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getBusinessHoursID() {
        return businessHoursID;
    }
}
