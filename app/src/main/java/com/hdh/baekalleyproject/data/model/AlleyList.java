package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AlleyList {

    @SerializedName("atlist")
    ArrayList<Alley> alleyArrayList;

    public ArrayList<Alley> getAlleyArrayList() {
        return alleyArrayList;
    }

    public void setAlleyArrayList(ArrayList<Alley> alleyArrayList) {
        this.alleyArrayList = alleyArrayList;
    }
}
