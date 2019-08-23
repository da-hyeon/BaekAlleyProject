package com.hdh.baekalleyproject.data.model;

public class WrongItem {
    private String wrongTitle;
    private boolean checkBoxStatus;

    public WrongItem(String wrongTitle, boolean checkBoxStatus) {
        this.wrongTitle = wrongTitle;
        this.checkBoxStatus = checkBoxStatus;
    }

    public String getWrongTitle() {
        return wrongTitle;
    }

    public void setCheckBoxStatus(boolean checkBoxStatus) {
        this.checkBoxStatus = checkBoxStatus;
    }

    public boolean isCheckBoxStatus() {
        return checkBoxStatus;
    }
}
