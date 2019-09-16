package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.RestaurantImageListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantMenuListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantReviewListAdapter;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.data.model.RestaurantDetail;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;
import com.hdh.baekalleyproject.ui.dialog.share.ShareDialog;
import com.hdh.baekalleyproject.ui.dialog.view_more.ViewMoreDialog;
import com.hdh.baekalleyproject.ui.modify_info.ModifyInfoActivity;
import com.hdh.baekalleyproject.ui.review_write.ReviewWriteActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailPresenter extends BaseActivityPresenter implements RestaurantDetailContract.Presenter, OnMapReadyCallback {
    private RestaurantDetailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private RestaurantDetail mRestaurantDetail;

    private double[] mLatLon;

    private UserInformation mUserInformation;

    private RestaurantImageListAdapter mRestaurantImageListAdapter;
    private RestaurantMenuListAdapter mRestaurantMenuListAdapter;
    private RestaurantReviewListAdapter mRestaurantReviewListAdapter;


    RestaurantDetailPresenter(RestaurantDetailContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mUserInformation = MyApplication.getUserInformationInstance();

        mRestaurantImageListAdapter = new RestaurantImageListAdapter(mContext);
        mRestaurantMenuListAdapter = new RestaurantMenuListAdapter(mContext);
        mRestaurantReviewListAdapter = new RestaurantReviewListAdapter(mContext , mActivity);
    }


    /**
     * 네이버 지도 세팅
     */
    @Override
    public void mapAsync(MapView map) {
        map.getMapAsync(this);
    }

    /**
     * RecyclerView 세팅
     *
     * @param rvImageView  이미지 뷰
     * @param rvMenuView   메뉴 뷰
     * @param rvReviewView 리뷰 뷰
     */
    @Override
    public void setRecyclerView(RecyclerView rvImageView, RecyclerView rvMenuView, RecyclerView rvReviewView) {

        LinearLayoutManager imageViewerLayoutManager = new LinearLayoutManager(mContext);
        imageViewerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImageView.setLayoutManager(imageViewerLayoutManager);
        rvImageView.setAdapter(mRestaurantImageListAdapter);

        LinearLayoutManager menuViewerLayoutManager = new LinearLayoutManager(mContext);
        menuViewerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuView.setLayoutManager(menuViewerLayoutManager);
        rvMenuView.setAdapter(mRestaurantMenuListAdapter);

        LinearLayoutManager reviewViewerLayoutManager = new LinearLayoutManager(mContext);
        reviewViewerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvReviewView.setLayoutManager(reviewViewerLayoutManager);
        rvReviewView.setAdapter(mRestaurantReviewListAdapter);

        setRecyclerViewAnimation(rvImageView);
    }


    /**
     * 뷰 세팅
     *
     * @param intent 이전 액티비티에서 전달받은 데이터
     */
    @Override
    public void setView(Intent intent) {

        mView.showLoading();

        String userID = "0";

        if (mUserInformation != null) {
            userID = mUserInformation.getId();
        }

        //전달받은 식당정보 받기
        String restaurantID = intent.getStringExtra(Constants.RESTAURANT_ID);

        if (restaurantID == null || restaurantID.equals("")) {
            Log.d("restaurantID Null", restaurantID);
        } else {
            Log.d("restaurantID Not Null", restaurantID);

            Call<RestaurantDetail> getRestaurantDetails = MyApplication
                    .getRestAdapter()
                    .getRestaurantDetail(
                            restaurantID,
                            userID);

            getRestaurantDetails.enqueue(new Callback<RestaurantDetail>() {
                @Override
                public void onResponse(@NonNull Call<RestaurantDetail> call, @NonNull Response<RestaurantDetail> response) {
                    if (response.isSuccessful()) {
                        mRestaurantDetail = response.body();

                        if (mRestaurantDetail != null) {

                            mRestaurantImageListAdapter.setRestaurantImageList(mRestaurantDetail.getRestaurantImageList());
                            mRestaurantMenuListAdapter.setRestaurantMenuList(mRestaurantDetail.getRestaurantMenuList());
                            mRestaurantReviewListAdapter.setRestaurantReviewList(mRestaurantDetail.getReviewList());

                            mRestaurantImageListAdapter.notifyDataSetChanged();
                            mRestaurantMenuListAdapter.notifyDataSetChanged();
                            mRestaurantReviewListAdapter.notifyDataSetChanged();

                            if (response.code() == 200) {
                                mLatLon = setMapLocation();
                                setRestaurantInformationView();

                            }

                        } else {
                            //mView.showFailDialog("실패" , "데이터 로딩 실패");
                            Log.d("실패", "데이터 로딩 실패");
                            mView.removeActivity();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantDetail> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                    Log.d("error", t.getLocalizedMessage());
                }
            });

        }
    }

    /**
     * 공유하기 클릭 이벤트 처리
     */
    @Override
    public void clickShare() {
        ShareDialog shareDialog = new ShareDialog(mContext, mActivity);
        shareDialog.setRestaurantData(mRestaurantDetail.getRestaurant().get(0));
        Objects.requireNonNull(shareDialog.getWindow()).getAttributes().windowAnimations = R.style.dialogAnimation;

        shareDialog.show();
    }

    /**
     * 더보기 클릭 이벤트 처리
     */
    @Override
    public void clickMore() {
        /*DisplayMetrics dm = mContext.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        ViewMoreDialog viewMoreDialog = new ViewMoreDialog(mContext);
        WindowManager.LayoutParams wm = viewMoreDialog.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(viewMoreDialog.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = width;  //화면 너비
        viewMoreDialog.getWindow().setGravity(Gravity.BOTTOM);
        viewMoreDialog.show();*/

        ViewMoreDialog viewMoreDialog = new ViewMoreDialog(mContext);
        viewMoreDialog.setRestaurantData(mRestaurantDetail.getRestaurant().get(0));
        Objects.requireNonNull(viewMoreDialog.getWindow()).getAttributes().windowAnimations = R.style.dialogAnimation;
        viewMoreDialog.show();

    }

    /**
     * 가야쥬 클릭 이벤트 처리
     */
    @Override
    public void clickNumberOfLikeText(boolean state) {

        //로그인일 때
        if (mUserInformation != null) {
            Call<String> requestRegisterAndDeleteWillGo = MyApplication
                    .getRestAdapter()
                    .requestRegistrationAndDeleteWillGo(
                            mRestaurantDetail.getRestaurant().get(0).getRestaurantID(),
                            mUserInformation.getId());

            requestRegisterAndDeleteWillGo.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.code() == 200) {
                        mView.changeNumberOfLikeText(!state);
                        //받은 데이터 뿌려주기
                        mView.setRestaurantNumberOfLike(response.body());
                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                    Log.d("error", t.getLocalizedMessage());
                }
            });
        }
        //비로그인일 때
        else {
            mView.moveLogin();
        }
    }

    /**
     * 리뷰쓰기 클릭 이벤트 처리
     */
    @Override
    public void clickWriteReview() {
        //로그인일 때
        if (mUserInformation != null) {
            Intent intent = new Intent(mContext, ReviewWriteActivity.class);
            intent.putExtra(Constants.RESTAURANT_ID, Integer.parseInt(mRestaurantDetail.getRestaurant().get(0).getRestaurantID()));
            mView.moveActivity(intent);
        } else {
            mView.moveLogin();
        }
    }

    /**
     * 맵 클릭 이벤트 처리
     */
    @Override
    public void clickMap() {

    }

    /**
     * 전화하기 클릭 이벤트 처리
     */
    @Override
    public void clickCall() {

    }

    /**
     * 주소복사 클릭 이벤트 처리
     */
    @Override
    public void clickAddressCopy() {
        //클립보드
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(Constants.RESTAURANT_ADDRESS, mRestaurantDetail.getRestaurant().get(0).getRestaurantAddress());
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
            mView.showToast("클립보드에 주소가 복사되었습니다.");
        } else {
            mView.showToast("주소 복사에 실패했습니다.");
        }
    }

    /**
     * 잘못된 정보 클릭 이벤트 처리
     */
    @Override
    public void clickWrongInfo() {
        Intent intent = new Intent(mContext, ModifyInfoActivity.class);
        mView.moveActivity(intent);
    }

    /**
     * 맛있네유 클릭 이벤트 처리
     */
    @Override
    public void clickGreat() {

    }

    /**
     * 괜찮네유 클릭 이벤트 처리
     */
    @Override
    public void clickGood() {

    }

    /**
     * 괜찮네유 클릭 이벤트 처리
     */
    @Override
    public void clickBad() {

    }

    /**
     * 식당정보 뷰 세팅
     */
    private void setRestaurantInformationView() {
        Restaurant restaurant = mRestaurantDetail.getRestaurant().get(0);

        mView.setRestaurantAlley(restaurant.getRestaurantAlley());
        mView.setRestaurantName(restaurant.getRestaurantName());
        mView.setRestaurantNumberOfView(restaurant.getRestaurantNumberOfView());
        mView.setRestaurantNumberOfReview(restaurant.getRestaurantNumberOfReview());
        mView.setRestaurantNumberOfLike(restaurant.getRestaurantNumberOfLike());
        //mView.setRanking();
        mView.setAddress(restaurant.getRestaurantAddress());
        mView.setUpdate(restaurant.getRestaurantUpdate());


        if (mRestaurantDetail.getBusinessHoursList().size() >= 1) {
            String businessHours = mRestaurantDetail.getBusinessHoursList().get(0).getBusinessHours().replace(",", "\n");
            mView.setBusinessHours(businessHours);
            mView.showBusinessHours();
        } else {
            //영업시간 숨기기
            mView.hideBusinessHours();
        }

        if (mRestaurantDetail.getBusinessHoursList().size() >= 2) {
            String breakTime = mRestaurantDetail.getBusinessHoursList().get(1).getBusinessHours().replace(",", "\n");
            mView.setBreakTime(breakTime);
            mView.showBreakTime();
        } else {
            //쉬는시간 숨기기
            mView.hideBreakTime();
        }

        if (mRestaurantDetail.getBusinessHoursList().size() >= 3) {
            String lastOrderTime = mRestaurantDetail.getBusinessHoursList().get(2).getBusinessHours().replace(",", "\n");
            mView.setLastOrderTime(lastOrderTime);
            mView.showLastOrderTime();
        } else {
            //마지막 주문 숨기기
            mView.hideLastOrderTime();
        }

        if (mRestaurantDetail.getBusinessHoursList().size() >= 4) {
            mView.setHoliday(mRestaurantDetail.getBusinessHoursList().get(3).getBusinessHours());
            mView.showHoliday();
        } else {
            //휴일 숨기기
            mView.hideHoliday();
        }

        if (mRestaurantDetail.getWillGoClickStatus() != null &&
                !mRestaurantDetail.getWillGoClickStatus().equals("0")) {
            mView.changeNumberOfLikeText(true);
        } else {
            mView.changeNumberOfLikeText(false);
        }

        mView.setPrice(mRestaurantDetail.getRestaurantPrice().get(0).getPrice());
        mView.setReviewCountOfGreat(mRestaurantDetail.getRestaurantReviewCountOfTastes().get(0).getTasteGreat());
        mView.setReviewCountOfGood(mRestaurantDetail.getRestaurantReviewCountOfTastes().get(0).getTasteGood());
        mView.setReviewCountOfBad(mRestaurantDetail.getRestaurantReviewCountOfTastes().get(0).getTasteBad());
    }

    /**
     * 주소를 위도 경도로 변환
     *
     * @return 위도, 경도 Array
     */
    private double[] setMapLocation() {

        Geocoder geocoder = new Geocoder(mContext);
        String address = "";
        //주소 입력

        if (mRestaurantDetail != null) {
            if (mRestaurantDetail.getRestaurant() != null) {
                address = mRestaurantDetail.getRestaurant().get(0).getRestaurantAddress();
                mView.hideLoading();
            }
        }
        double lat = 0, lon = 0;

        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(address, 10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constants.MAP_ERROR_TAG, "입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Log.e(Constants.MAP_ERROR_TAG, "해당되는 주소 정보는 없습니다");
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
            }
        }

        return new double[]{lat, lon};
    }

    /**
     * 네이버지도 설정
     */
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        //double[] latlon = setMapLocation();
        //위도와 경도 을 지정
        if (mLatLon != null) {
            LatLng location = new LatLng(mLatLon[0], mLatLon[1]);
            Marker marker = new Marker();
            marker.setPosition(location);

            marker.setMap(naverMap);
            marker.setIcon(OverlayImage.fromResource(R.drawable.icon_restaurant_spot));

            // 카메라 위치와 줌 조절(숫자가 클수록 확대)
            CameraPosition cameraPosition = new CameraPosition(location, 17);
            naverMap.setCameraPosition(cameraPosition);
        }

        // 줌 범위 제한
        naverMap.setMinZoom(5.0);   //최소
        naverMap.setMaxZoom(18.0);  //최대

        // 카메라 영역 제한
        LatLng northWest = new LatLng(31.43, 122.37);   //서북단
        LatLng southEast = new LatLng(44.35, 132);      //동남단
        naverMap.setExtent(new LatLngBounds(northWest, southEast));
        naverMap.setMapType(NaverMap.MapType.Basic);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setLogoClickEnabled(false);
        uiSettings.setScrollGesturesEnabled(false);
        uiSettings.setZoomGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);

    }

//    /**
//     * 저장된 유저 정보 불러오기
//     */
//    private void loadUser() {
//        String json = mPrefs.getString(Constants.USER_SAVE_DATA, null);
//        if (json != null) {
//            mUserInformation = mGson.fromJson(json, UserInformation.class);
//        }
//    }

}
