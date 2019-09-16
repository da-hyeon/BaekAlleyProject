package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {

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
    private boolean likeClickStatus;

    @SerializedName("rw_regdate")
    private String reviewRegistrationDate;

    @SerializedName("rw_update")
    private String reviewUpdateDate;

    @SerializedName("rw_count")
    private int reviewLikeCount;

    @SerializedName("rw_type")
    private int revivewTasteType;

    @SerializedName("cmnt_count")
    private int commentCount;

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

    public boolean isLikeClickStatus() {
        return likeClickStatus;
    }

    public void setLikeClickStatus(boolean likeClickStatus) {
        this.likeClickStatus = likeClickStatus;
    }

    public String getReviewUpdateDate() {
        return reviewUpdateDate;
    }

    public int getReviewLikeCount() {
        return reviewLikeCount;
    }

    public void setReviewLikeCount(int reviewLikeCount) {
        this.reviewLikeCount = reviewLikeCount;
    }

    public int getRevivewTasteType() {
        return revivewTasteType;
    }

    public int getCommentCount() {
        return commentCount;
    }
}
