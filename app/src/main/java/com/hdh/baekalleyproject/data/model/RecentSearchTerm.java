package com.hdh.baekalleyproject.data.model;

public class RecentSearchTerm {
    private String recentSearchTerm;
    private String date;

    public RecentSearchTerm(String recentSearchTerm, String date) {
        this.recentSearchTerm = recentSearchTerm;
        this.date = date;
    }

    public String getRecentSearchTerm() {
        return recentSearchTerm;
    }

    public void setRecentSearchTerm(String recentSearchTerm) {
        this.recentSearchTerm = recentSearchTerm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
