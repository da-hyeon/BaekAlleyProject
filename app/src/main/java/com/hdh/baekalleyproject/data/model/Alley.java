package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class Alley {
    @SerializedName("ay_nm")
    private String alleyName;

    @SerializedName("ay_idx")
    private String alleyID;

    private String tag = "0";

    public String getAlleyName() {
        return alleyName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAlleyID() {
        return alleyID;
    }
}