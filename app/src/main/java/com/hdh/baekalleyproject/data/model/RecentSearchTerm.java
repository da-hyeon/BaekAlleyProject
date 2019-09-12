package com.hdh.baekalleyproject.data.model;

import android.support.annotation.NonNull;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RecentSearchTerm extends RealmObject implements Comparable<RecentSearchTerm> {

    @PrimaryKey
    private int id;

    private String recentSearchTerm;
    private Date date;
    private String elapsedTime;

    public RecentSearchTerm() {
    }


    public int getId() {
        return id;
    }

    public String getRecentSearchTerm() {
        return recentSearchTerm;
    }

    public void setRecentSearchTerm(String recentSearchTerm) {
        this.recentSearchTerm = recentSearchTerm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public int compareTo(@NonNull RecentSearchTerm recentSearchTerm) {
        if (this.id > recentSearchTerm.id)
            return -1;
        else
            return 1;
    }
}
