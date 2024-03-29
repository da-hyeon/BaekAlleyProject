package com.hdh.baekalleyproject;

public class Constants {

    /**
     *
     */
    public static final String RANK_STATUS_CONFIRMATION = "확정";
    public static final String RANK_STATUS_DATE_NOT_SET = "날짜미정";
    public static final String RANK_STATUS_EVALUATION_NOT_DECIDED = "평가미정";

    public static final int REVIEW_TASTE_ALL = 0;
    public static final int REVIEW_TASTE_GREAT = 1;
    public static final int REVIEW_TASTE_GOOD = 2;
    public static final int REVIEW_TASTE_BAD = 3;

    public static final int ITEM_TYPE_RESTAURANT = 0;
    public static final int ITEM_TYPE_REVIEW = 1;
    public static final int ITEM_TYPE_COMMENT = 2;

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

    public static final String REVIEW_FILTER_TYPE = "reviewType";


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

    public static final String REQUEST_FILTER_ITEM = "api/rstrn/rstrnFiltertest";

    public static final String REQUEST_REGISTRATION_AND_DELETE_WILL_GO = "api/rstrn/rstrnLike";

    public static final String REQUEST_REGISTRATION_REVIEW = "api/rv/reviewWriter";
    public static final String SELECT_REGISTRATION_REVIEW_FILTER = "api/rv//reviewFilterTest";

    public static final String REQUEST_REGISTRATION_REVIEW_LIKE = "api/rv/reviewLike";

    public static final String REQUEST_REGISTRATION_REVIEW_COMMENT = "api/rv/commentWriter";
    public static final String SELECT_REGISTRATION_REVIEW_COMMENT = "api/rv/reviewDetails";

    public static final String REQUEST_REGISTRATION_REVIEW_COMMENT_LIKE = "api/rv/commentLike";
}
