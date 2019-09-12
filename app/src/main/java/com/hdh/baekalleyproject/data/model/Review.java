package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("rw_idx")
    private String reviewID;

    @SerializedName("mb_idx")
    private String userID;

    @SerializedName("rt_idx")
    private String restaurantID;

    @SerializedName("mb_nm")
    private String userName;

    @SerializedName("mb_img")
    private String userImageURL;

    @SerializedName("rw_menu")
    private String reviewMenu;

    @SerializedName("rw_title")
    private String reviewTitle;

    @SerializedName("rw_contents")
    private String reviewContent;

    @SerializedName("rw_check")
    private boolean willGoClickStatus;

    @SerializedName("rw_regdate")
    private String reviewRegistrationDate;

    @SerializedName("rw_update")
    private String reviewUpdateDate;

    @SerializedName("rw_count")
    private String reviewLikeCount;

    @SerializedName("rw_type")
    private String revivewTasteType;

    public String getReviewID() {
        return reviewID;
    }

    public String getUserID() {
        return userID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getReviewMenu() {
        return reviewMenu;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewRegistrationDate() {
        return reviewRegistrationDate;
    }

    public boolean isWillGoClickStatus() {
        return willGoClickStatus;
    }

    public String getReviewUpdateDate() {
        return reviewUpdateDate;
    }

    public String getReviewLikeCount() {
        return reviewLikeCount;
    }

    public String getRevivewTasteType() {
        return revivewTasteType;
    }
}
