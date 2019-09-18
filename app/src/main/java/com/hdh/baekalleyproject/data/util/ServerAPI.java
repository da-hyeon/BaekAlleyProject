package com.hdh.baekalleyproject.data.util;


import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.data.model.AlleyList;
import com.hdh.baekalleyproject.data.model.RestaurantDetail;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.data.model.ReviewCommentList;
import com.hdh.baekalleyproject.data.model.ReviewList;
import com.hdh.baekalleyproject.data.model.UserInformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {

    /**
     * 식당 전체목록 가져오기
     */
    @GET(Constants.SELECT_RESTAURANT)
    Call<RestaurantList> getRestaurantList();


    /**
     * 식당 디테일 정보 가져오기
     *
     * @param restaurantID 식당 ID
     * @return 식당 디테일 정보
     */
    @FormUrlEncoded
    @POST(Constants.SELECT_RESTAURANT_DETAIL)
    Call<RestaurantDetail> getRestaurantDetail(
            @Field("rt_idx") String restaurantID ,
            @Field("mb_idx") String userID);

    /**
     * 골목 전체목록 가져오기
     */
    @GET(Constants.SELECT_ALLEY)
    Call<AlleyList> getAlleyList();


    /**
     * 필터 요청
     *
     * @param ayOption 골목 선택 : 1 , 미선택 : 0
     * @param cgOption 음식종류 선택 : 1 , 미선택 : 0
     * @param PriceOption 가격대 선택 : 1 , 미선택 : 0
     * @param ay_nm 선택한 골목 리스트
     * @param cg_nm 선택한 음식종류 리스트
     * @param avrg_Price 선택한 가격대 리스트
     * @param optionCount 선택한 필터의 카테고리 갯수
     * @return 식당 리스트
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_FILTER_ITEM)
    Call<RestaurantList> setFilter(
            @Field("ayOption") int ayOption,
            @Field("cgOption") int cgOption,
            @Field("priceOption") int PriceOption,
            @Field("ay_nm") ArrayList<String> ay_nm,
            @Field("cg_nm") ArrayList<String> cg_nm ,
            @Field("avrg_Price") ArrayList<String> avrg_Price,
            @Field("optionCount") int optionCount);


    /**
     * 회원 가입 요청 (카카오)
     *
     * @param userEmail 회원 email
     * @param userName 회원 이름
     * @param userImageURL 회원의 프로필 이미지 URL
     * @param userAppVersion 회원이 설치한 앱의 버전
     * @param userDeviceCode 회원의 기기코드
     * @param userNotificationConsentStatus 회원의 알림수신여부
     * @return UserInformation
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_KAKAO)
    Call<UserInformation> requestRegistrationKAKAO(
            @Field("mb_id") String userEmail,
            @Field("mb_nm") String userName,
            @Field("mb_img") String userImageURL,
            @Field("mb_version") String userAppVersion ,
            @Field("mb_push_code") String userDeviceCode ,
            @Field("mb_ntyn") String userNotificationConsentStatus);

    /**
     * 로그인 로그 등록 요청
     *
     * @param email 사용자의 email
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_LOGIN_LOG)
    Call<Void> requestRegistrationLoginLog(
            @Field("mb_id") String email);

    /**
     * 가야쥬 등록,삭제 요청
     * @param restaurantID 식당 ID
     * @param userID 회원 ID
     * @return rstrn_like 가야쥬 개수
     *
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_AND_DELETE_WILL_GO)
    Call<String> requestRegistrationAndDeleteWillGo(
            @Field("rt_idx") String restaurantID ,
            @Field("mb_idx") String userID );

    /**
     * 리뷰 등록 요청
     *
     * @param reviewTitle 리뷰 제목
     * @param reviewContents 리뷰 내용
     * @param selectedTasteScore 선택한 맛의 점수
     * @param selectedTasteType 선택한 맛의 타입
     * @param restaurantID 식당 ID
     * @param userID 회원 ID
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_REVIEW)
    Call<Void> requestRegistrationReview(
            @Field("rw_title") String reviewTitle ,
            @Field("rw_menu") String reviewMenu ,
            @Field("rw_contents") String reviewContents ,
            @Field("rw_score") double selectedTasteScore ,
            @Field("rw_type") String selectedTasteType ,
            @Field("rt_idx") int restaurantID ,
            @Field("mb_idx") int userID );

    /**
     * 리뷰 좋아요 등록,삭제 요청
     * @param restaurantID 식당 ID
     * @param userID 회원 ID
     * @return rev_count  - 좋아요 개수
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_REVIEW_LIKE)
    Call<Integer> requestRegistrationReviewLike(
            @Field("rt_idx") String restaurantID ,
            @Field("mb_idx") String userID ,
            @Field("rw_idx") String reviewID);


    /**
     * 리뷰 댓글 등록 요청
     *
     * @param comment 댓글내용
     * @param userID 회원번호
     * @param reviewID 리뷰번호
     * @param restaurantID 식당번호
     * @return 댓글 수
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_REVIEW_COMMENT)
    Call<Void> requestRegistrationReviewComment(
            @Field("cmnt_contents") String comment ,
            @Field("mb_idx") String userID ,
            @Field("rw_idx") String reviewID,
            @Field("rt_idx") String restaurantID);

    /**
     * 리뷰 댓글 조회
     * @param userID 회원번호
     * @param reviewID 리뷰번호
     * @param restaurantID 식당번호
     * @return 댓글
     */
    @FormUrlEncoded
    @POST(Constants.SELECT_REGISTRATION_REVIEW_COMMENT)
    Call<ReviewCommentList> selectRegistrationReviewComment(
            @Field("mb_idx") String userID ,
            @Field("rw_idx") String reviewID,
            @Field("rt_idx") String restaurantID);


    /**
     * 댓글 좋아요 등록,삭제 요청
     *
     * @param restaurantID 식당 ID
     * @param reviewID 리뷰 ID
     * @param userID 회원 ID
     * @param commentID 댓글 ID
     * @return rev_count  - 좋아요 개수
     */
    @FormUrlEncoded
    @POST(Constants.REQUEST_REGISTRATION_REVIEW_COMMENT_LIKE)
    Call<Integer> requestRegistrationReviewCommentLike(
            @Field("rt_idx") String restaurantID ,
            @Field("rw_idx") String reviewID,
            @Field("mb_idx") String userID ,
            @Field("cmnt_idx") String commentID
            );


    /**
     * 리뷰 필터 요청
     *
     * @param restaurantID 식당 ID
     * @param userID 회원 ID
     * @param reviewType 리뷰 타입
     * @return 리뷰
     */
    @FormUrlEncoded
    @POST(Constants.SELECT_REGISTRATION_REVIEW_FILTER)
    Call<ReviewList> selectRegistrationReviewFilter(
            @Field("rt_idx") String restaurantID ,
            @Field("mb_idx") String userID ,
            @Field("rw_type") int reviewType);

//    /**
//     * 로그인 요청
//     *
//     * @param userEmail
//     * @param userPassword
//     * @return
//     */
//    @GET(Constants.USER_LOGIN_REQUEST_URL)
//    Call<UserInfo> getFineUser(@Query("userEmail") String userEmail, @Query("userPassword") String userPassword);
//
//    /**
//     * 회원가입 요청
//     *
//     * @param userName
//     * @param userEmail
//     * @param userPassword
//     * @param userEasyPassword
//     * @param userRN
//     * @param userPhone
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.USER_REGISTER_REQUEST_URL)
//    Call<UserInfo> setUserRegister(
//            @Field("username") String userName,
//            @Field("email") String userEmail,
//            @Field("password") String userPassword,
//            @Field("easypassword") String userEasyPassword,
//            @Field("security_number") String userRN,
//            @Field("phone") String userPhone);
//
//
//    /**
//     * 그룹생성 요청
//     *
//     * @param groupaname
//     * @param usercode
//     * @param groupcontent
//     * @param peoplenumber
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.CREATE_GROUP_REQUEST_URL)
//    Call<Void> createGroup(
//            @Field("groupaname") String groupaname,
//            @Field("usercode") String usercode,
//            @Field("groupcontent") String groupcontent,
//            @Field("peoplenumber") String peoplenumber);
//
//
//    /**
//     * 그룹목록 요청
//     *
//     * @param usercode
//     * @return
//     */
//    @GET(Constants.SELECT_GROUP_REQUEST_URL)
//    Call<MyGroup> getGroupList(@Query("usercode") String usercode);
//
//    /**
//     * 그룹목록 요청2
//     *
//     * @param usercode
//     * @return
//     */
//    @GET(Constants.SELECT_GROUP_REQUEST_URL_2)
//    Call<MyGroup> getGroupList2(@Query("usercode") String usercode);
//
//    /**
//     * 그룹삭제 요청
//     *
//     * @param groupacode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.DELETE_GROUP_REQUEST_URL)
//    Call<Void> deleteGroup(
//            @Field("groupacode") String groupacode);
//
//
//
//    @FormUrlEncoded
//    @POST(Constants.UPDATE_GROUP_REQUEST_URL)
//            Call<Void> updateGroup(
//            @Field("groupacode") String groupacode,
//            @Field("groupcontent") String groupcontent,
//            @Field("peoplenumber") String peoplenumber);
//
//
//    /**
//     * 등록할 카드목록가져오기
//     */
//    @GET(Constants.DUTCHPAY_CARD_COMPANY_SELECT)
//
//    Call<CardCompanyList> getCardSelectList();
//
//    /**
//     * 카드등록 요청
//     *
//     * @param cardno
//     * @param cardtypecode
//     * @param usercode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_CARD_REGISTER)
//    Call<Void> setCardRegister(
//            @Field("cardno") String cardno,
//            @Field("cardtypecode") String cardtypecode,
//            @Field("usercode") String usercode);
//
//    /**
//     * 등록한 카드목록 요청
//     *
//     * @param usercode
//     */
//    @GET(Constants.DUTCHPAY_CARD_REGISTER_SELECT)
//    Call<CardRegisterList> getRegisterCardList(
//            @Query("usercode") String usercode);
//
//
//    /**
//     * 대표카드 설정 요청
//     *
//     * @param maincardcode
//     * @param subcardcode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_CARD_REPRESENTATIVE_CARD)
//    Call<Void> setCardRepresentativeCard(
//            @Field("maincardcode") String maincardcode,
//            @Field("subcardcode") String subcardcode
//    );
//
//
//    /**
//     * 카드삭제 요청
//     *
//     * @param cardcode
//     */
//    @GET(Constants.DUTCHPAY_CARD_DELETE)
//    Call<Void> setCardDelete(
//            @Query("cardcode") String cardcode);
//
//
//    /**
//     *
//     * 은행 목록가져오기
//     */
//    @GET(Constants.DUTCHPAY_ACCOUNT_BANK_SELECT)
//    Call<AccountBankList> getBankSelectList();
//
//    /**
//     * 계좌등록 요청
//     *
//     * @param accountno
//     * @param accounttypecode
//     * @param usercode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_ACCOUNT_REGISTER)
//    Call<Void> setAccountRegister(
//            @Field("accountno") String accountno,
//            @Field("accounttypecode") String accounttypecode,
//            @Field("usercode") String usercode);
//
//    /**
//     * 등록한 계좌목록 요청
//     *
//     * @param usercode
//     */
//    @GET(Constants.DUTCHPAY_ACCOUNT_REGISTER_SELECT)
//    Call<AccountRegisterList> getAccountRegisterList(
//            @Query("usercode") String usercode);
//
//
//    /**
//     * 대표계좌 설정 요청
//     *
//     * @param mainaccountcode
//     * @param subaccountcode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_ACCOUNT_REPRESENTATIVE_ACCOUNT)
//    Call<Void> setAccountRepresentativeCard(
//            @Field("mainaccountcode") String mainaccountcode,
//            @Field("subaccountcode") String subaccountcode
//    );
//
//    /**
//     * 계좌삭제 요청
//     *
//     * @param accountcode
//     */
//    @GET(Constants.DUTCHPAY_ACCOUNT_DELETE)
//    Call<Void> setAccountDelete(
//            @Query("accountcode") String accountcode);
//
//
//    /**
//     * 포인트 보내기
//     *
//     * @param giveamount
//     * @param buttonnumber
//     * @param usercode
//     * @param pointqrcode
//     * @param phonenumber
//     * @param username
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_POINT_SEND)
//    Call<Void> setPointSend(
//            @Field("giveamount") String giveamount,
//            @Field("buttonnumber") String buttonnumber,
//            @Field("usercode") String usercode,
//            @Field("pointqrcode") String pointqrcode,
//            @Field("phonenumber") String phonenumber,
//            @Field("username") String username
//    );
//
//    /**
//     * 포인트 충전
//     * 상품권 번호로 충전
//     *
//     * @param qrcode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_POINT_RECEIVE)
//    Call<Void> getPointReceive(
//            @Field("qrcode") String qrcode
//    );
//
//    /**
//     * 사용내역 목록 요청
//     *
//     * @param buttontype
//     * @param usercode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_PAY_USAGE_HISTORY)
//    Call<PayHistoryList> getUsageHistoryList(
//            @Field("buttontype") String buttontype,
//            @Field("usercode") String usercode
//    );
//
//    /**
//     * 그룹 업데이트 요청
//     *
//     * @param groupacode
//     * @param groupcontent
//     * @param peoplenumber
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.UPDATE_GROUP_REQUEST_URL)
//    Call<Void> updateGroup(
//            @Field("groupacode") String groupacode,
//            @Field("groupaname") String groupaname,
//            @Field("groupcontent") String groupcontent,
//            @Field("peoplenumber") String peoplenumber);
//
//    /**
//     * 진행중 이벤트 요청
//     *
//     * @return
//     */
//    @GET(Constants.SELECT_EVENT_ONGOING_REQUEST_URL)
//    Call<EventList> selectOnGoingEvent();
//
//    /**
//     * 진행종료 이벤트 요청
//     *
//     * @return
//     */
//    @GET(Constants.SELECT_EVENT_ENDPROGRESS_REQUEST_URL)
//    Call<EventList> selectEndProgressEvent();
//
//    /**
//     * 이메일 변경 요청
//     *
//     * @param email
//     * @param usercode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.CHANGE_EMAIL_REQUEST_URL)
//    Call<Void> changeEmail(@Field("email") String email,
//                           @Field("usercode") String usercode);
//
//    /**
//     * 비밀번호 변경 요청
//     *
//     * @param password
//     * @param usercode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.CHANGE_PASSWORD_REQUEST_URL)
//    Call<Void> changePassword(@Field("password") String password,
//                              @Field("usercode") String usercode);
//
//    /**
//     * 더치페이 내역 요청
//     *
//     * @param usercode
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_HISTORY_REQUEST_URL)
//    Call<Dutchpayhistory> getDutchapyHistoryList(@Field("usercode") String usercode);
//
//    /**
//     * 더치페이 내역 상세 요청
//     *
//     * @param usercode
//     * @param dutchpaycode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_DETAIL_REQUEST_URL)
//    Call<DutchpayDetail> getDutchpayDetail(
//            @Field("usercode") String usercode,
//            @Field("dutchpaycode") int dutchpaycode);
//
//    /**
//     * 더치페이 시작 요청
//     *
//     * @param usercode
//     * @param dutchpay_title
//     * @param total_price
//     * @param dutchpay_content
//     * @param dutchpay_message
//     * @param user_list1
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_NEW_REQUEST_URL)
//    Call<Void> setNewDutchpay(
//            @Field("usercode") int usercode,
//            @Field("dutchpay_title") String dutchpay_title,
//            @Field("total_price") int total_price,
//            @Field("dutchpay_content") String dutchpay_content,
//            @Field("dutchpay_message") String dutchpay_message,
//            @Field("user_list1") String user_list1);
//
//    /**
//     * 더치페이 참가자 송금 요청
//     * @param usercode_leader
//     * @param usercode_self
//     * @param dutchpaycode
//     * @param dutchpaypcode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.DUTCHPAY_MEMBER_PAY_REQUEST_URL)
//    Call<Void> setMemberDutchpay(
//            @Field("usercode_leader") int usercode_leader,
//            @Field("usercode_self") int usercode_self,
//            @Field("dutchpaycode") int dutchpaycode,
//            @Field("dutchpaypcode") int dutchpaypcode);
//
//    /**
//     * 전화번호 변경 요청
//     *
//     * @param phone
//     * @param usercode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.CHANGE_PHONENUMBER_REQUEST_URL)
//    Call<Void> changePhoneNumber(@Field("phone") String phone,
//                                 @Field("usercode") String usercode);
//
//    /**
//     * 이메일 찾기 요청
//     *
//     * @param name
//     * @param phone
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.SEARCH_EMAIL_REQUEST_URL)
//    Call<SearchEmail> findEmail(@Field("name") String name,
//                                @Field("phone") String phone);
//
//    /**
//     * 비밀번호 찾기 요청
//     *
//     * @param email
//     * @param name
//     * @param phone
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.SEARCH_PHONENUMBER_REQUEST_URL)
//    Call<SearchPassword> findPhoneNumber(@Field("email") String email,
//                                         @Field("name") String name,
//                                         @Field("phone") String phone);
//
//    /**
//     * 등록된 계좌 요청
//     *
//     * @param usercode
//     * @return
//     */
//    @GET(Constants.SELECT_ACCOUNT_REQUEST_URL)
//    Call<AccountList> selectAccount(@Query("usercode") String usercode);
//
//
//    /**
//     * QR코드 상품정보 요청
//     *
//     * @param qrcode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.SELECT_PRODUCT_QRCODE_REQUEST_URL)
//    Call<Product> selectQRCodeProduct(@Field("qrcode") String qrcode);
//
//
//    /**
//     * 결제번호 상품정보 요청
//     *
//     * @param payproduct_code
//     * @return
//     */
//
//    @FormUrlEncoded
//    @POST(Constants.SELECT_PRODUCT_PAYMENT_NUMBER_REQUEST_URL)
//    Call<Product> selectPaymentNumberProduct(@Field("payproduct_code") String payproduct_code);
//
//    /**
//     * 개인결제 결제 요청
//     *
//     * @param qrcode
//     * @param usercode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.UPDATE_PAYMENT_QRCODE_REQUEST_URL)
//    Call<String> updateQRCodePayment(@Field("qrcode") String qrcode,
//                                     @Field("usercode") String usercode);
//
//
//    /**
//     * 회원탈퇴 요청
//     *
//     * @param usercode
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(Constants.USER_DELETE_REQUEST_URL)
//    Call<Void> withdrawal(
//            @Field("usercode") String usercode);

}
