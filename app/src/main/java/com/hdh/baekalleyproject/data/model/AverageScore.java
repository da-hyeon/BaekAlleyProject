package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class AverageScore {

    @SerializedName("arge_check")
    private String state;

    @SerializedName("star_arge")
    private String averageScore;

    public String getState() {
        return state;
    }

    public String getAverageScore() {
        return averageScore;
    }
}
