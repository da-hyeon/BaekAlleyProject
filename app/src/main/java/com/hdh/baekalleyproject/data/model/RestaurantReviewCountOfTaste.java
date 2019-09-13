package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantReviewCountOfTaste {
    @SerializedName("rw_dlcs")
    private String tasteGreat;

    @SerializedName("rw_nrml")
    private String tasteGood;

    @SerializedName("rw_notgd")
    private String tasteBad;

    public String getTasteGreat() {
        return tasteGreat;
    }

    public String getTasteGood() {
        return tasteGood;
    }

    public String getTasteBad() {
        return tasteBad;
    }
}
