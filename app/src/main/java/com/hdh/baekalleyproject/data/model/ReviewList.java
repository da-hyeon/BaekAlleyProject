package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewList {

    @SerializedName("Filter_list")
    private ArrayList<Review> reviewList;

    @SerializedName("maxPage")
    private int maxPage;

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public int getMaxPage() {
        return maxPage;
    }
}
