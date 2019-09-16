package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class ReviewComment {

    @SerializedName("cmnt_count")
    private String countOfLike;

    @SerializedName("cmnt_contents")
    private String content;

    @SerializedName("mb_nm")
    private String userName;

    @SerializedName("cmnt_check")
    private boolean likeCheck;

    @SerializedName("mb_img")
    private String userImageURL;

    @SerializedName("cmnt_idx")
    private String id;

    @SerializedName("cmnt_regdate")
    private String registrationDate;

    @SerializedName("cmnt_update")
    private String updateDate;

    public String getCountOfLike() {
        return countOfLike;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isLikeCheck() {
        return likeCheck;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getId() {
        return id;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
