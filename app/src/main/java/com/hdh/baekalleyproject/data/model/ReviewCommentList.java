package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewCommentList {
    @SerializedName("commnt_List")
    private ArrayList<ReviewComment> reviewCommentList;

    public ArrayList<ReviewComment> getReviewCommentList() {
        return reviewCommentList;
    }
}
