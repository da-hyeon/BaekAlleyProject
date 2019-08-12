package com.hdh.baekalleyproject.data.model;

public class Alley {
    private String alleyName;
    private String tag;

    public Alley(String alleyName) {
        this.alleyName = alleyName;
        tag = "0";
    }

    public String getAlleyName() {
        return alleyName;
    }

    public void setAlleyName(String alleyName) {
        this.alleyName = alleyName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}