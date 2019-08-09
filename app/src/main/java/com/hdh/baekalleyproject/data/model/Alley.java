package com.hdh.baekalleyproject.data.model;

public class Alley {
    private String alleyName;

    public Alley(String alleyName) {
        this.alleyName = alleyName;
    }

    public String getAlleyName() {
        return alleyName;
    }

    public void setAlleyName(String alleyName) {
        this.alleyName = alleyName;
    }
}
