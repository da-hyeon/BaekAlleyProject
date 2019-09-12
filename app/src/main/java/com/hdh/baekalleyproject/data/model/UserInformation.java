package com.hdh.baekalleyproject.data.model;

import com.google.gson.annotations.SerializedName;

public class UserInformation {
    @SerializedName("mb_idx")
    private String id;

    @SerializedName("mb_nm")
    private String name;

    @SerializedName("mb_img")
    private String imageURL;

    @SerializedName("mb_id")
    private String email;

    @SerializedName("mb_pwd")
    private String password;

    @SerializedName("mb_tel")
    private String phoneNumber;

    @SerializedName("mb_version")
    private String appVersion;

    @SerializedName("mb_regdate")
    private String registerDate;

    @SerializedName("mb_update")
    private String updateDate;

    @SerializedName("mb_push_code")
    private String deviceCode;

    @SerializedName("mb_ntyn")
    private String notificationConsentStatus;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getNotificationConsentStatus() {
        return notificationConsentStatus;
    }
}
