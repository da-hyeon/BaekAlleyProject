package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantImage {

    @SerializedName("ri_order")
    private String imageOrder;

    @SerializedName("rt_idx")
    private String imageID;

    @SerializedName("ri_idx")
    private String RestaurantID;

    @SerializedName("ri_url")
    private String imageURL;

    public String getImageOrder() {
        return imageOrder;
    }

    public String getImageID() {
        return imageID;
    }

    public String getRestaurantID() {
        return RestaurantID;
    }

    public String getImageURL() {
        return imageURL;
    }
}
