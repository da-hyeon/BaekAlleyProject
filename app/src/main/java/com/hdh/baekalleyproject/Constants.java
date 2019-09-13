package com.hdh.baekalleyproject;

public class Constants {


    /**
     * INTENT TAG
     */
    public static final String RESTAURANT_ID = "restaurantID";
    public static final String RESTAURANT_ADDRESS = "restaurantAddress";
    public static final String RESTAURANT_IMAGES = "restaurantImages";
    public static final String RESTAURANT_IMAGE_CLICK_POSITION = "restaurantImageClickPosition";

    public static final String MAIN_ADAPTER = "mainAdapter";
    public static final String FILTER_ADAPTER = "filterAdapter";

    public static final String REVIEW_DATA = "reviewData";

    /**
     * SharedPreference
     */
    public static final String FILTER_SAVE_DATA = "filterSaveData";
    public static final String SEARCH_SAVE_DATA = "searchSaveData";
    public static final String USER_SAVE_DATA = "userSaveData";
    /**
     * LOG TAG
     */
    public static final String MAP_ERROR_TAG = "mapErrorTag";


    /**
     * Restful API
     */
    public static final String REQUEST_REGISTRATION_KAKAO = "api/mb/kakaoLogin";
    public static final String REQUEST_REGISTRATION_LOGIN_LOG = "api/mb/loginLog";

    public static final String SELECT_RESTAURANT = "api/rstrn/rstrnList";
    public static final String SELECT_RESTAURANT_DETAIL = "api/rstrn/rstrnDetail";

    public static final String SELECT_ALLEY = "api/rstrn/selectAynm";

    public static final String REQUEST_FILTER_ITEM = "api/rstrn/rstrnFilter";

    public static final String REQUEST_REGISTRATION_AND_DELETE_WILL_GO = "api/rstrn/rstrnLike";

    public static final String REQUEST_REGISTRATION_REVIEW = "api/rv/reviewWriter";

    public static final String REQUEST_REGISTRATION_REVIEW_LIKE = "api/rv/reviewLike";

    public static final String REQUEST_REGISTRATION_REVIEW_COMMENT = "api/rv/commentWriter";
}
