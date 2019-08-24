package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.app.Activity;
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
import com.hdh.baekalleyproject.data.model.Review;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailPresenter extends BaseActivityPresenter implements RestaurantDetailContract.Presenter , OnMapReadyCallback {
    private RestaurantDetailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private RestaurantDetail mRestaurantDetail;

    RestaurantDetailPresenter(RestaurantDetailContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }


    /**
     * 네이버 지도 세팅
     */
    @Override
    public void mapAsync(MapView map) {
        map.getMapAsync(this);
    }

    /**
     * 뷰 세팅
     * @param intent 이전 액티비티에서 전달받은 데이터
     * @param rvImageView 이미지 뷰
     * @param rvMenuView 메뉴 뷰
     * @param rvReviewView 리뷰 뷰
     */
    @Override
    public void setView(Intent intent , RecyclerView rvImageView , RecyclerView rvMenuView , RecyclerView rvReviewView) {

        //전달받은 식당정보 받기
        String restaurantID = intent.getStringExtra(Constants.RESTAURANT_ID);


        Call<RestaurantDetail> getRestaurantDetails = MyApplication
                .getRestAdapter()
                .getRestaurantDetails(restaurantID);

        getRestaurantDetails.enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantDetail> call, @NonNull Response<RestaurantDetail> response) {
                if (response.isSuccessful()) {
                    mRestaurantDetail = response.body();

                    if (mRestaurantDetail != null) {

                        LinearLayoutManager imageViewerLayoutManager = new LinearLayoutManager(mContext);
                        imageViewerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        rvImageView.setLayoutManager(imageViewerLayoutManager);
                        rvImageView.setAdapter(new RestaurantImageListAdapter( mRestaurantDetail.getRestaurantImageList() , mContext));


                        LinearLayoutManager menuViewerLayoutManager = new LinearLayoutManager(mContext);
                        menuViewerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rvMenuView.setLayoutManager(menuViewerLayoutManager);
                        rvMenuView.setAdapter(new RestaurantMenuListAdapter( mRestaurantDetail.getRestaurantMenuList() , mContext));

                        setView();
                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                        mView.removeActivity();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantDetail> call, @NonNull Throwable t) {
                Log.d("error" , t.getMessage());
                Log.d("error" , t.getLocalizedMessage());
            }
        });

        ArrayList<Review> reviewArrayList = new ArrayList<>();
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());
        reviewArrayList.add(new Review());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvReviewView.setLayoutManager(linearLayoutManager2);
        rvReviewView.setAdapter(new RestaurantReviewListAdapter( reviewArrayList , mContext));

    }

    /**
     * 공유하기 클릭 이벤트 처리
     */
    @Override
    public void clickShare() {
        ShareDialog shareDialog = new ShareDialog(mContext , mActivity);
        shareDialog.setRestaurantData(mRestaurantDetail.getRestaurant().get(0));
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
        viewMoreDialog.show();

    }

    /**
     * 가야쥬 클릭 이벤트 처리
     */
    @Override
    public void clickNumberOfLikeText(boolean state) {
        mView.changeNumberOfLikeText(!state);
    }

    /**
     * 리뷰쓰기 클릭 이벤트 처리
     */
    @Override
    public void clickWriteReview() {
        Intent intent = new Intent(mContext , ReviewWriteActivity.class);
        mView.moveActivity(intent);
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

    }

    /**
     * 잘못된 정보 클릭 이벤트 처리
     */
    @Override
    public void clickWrongInfo() {
        Intent intent = new Intent(mContext , ModifyInfoActivity.class);
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
    private void setView(){
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

        mView.setPrice(mRestaurantDetail.getRestaurantPrice().get(0).getPrice());
    }

    /**
     * 주소를 위도 경도로 변환
     * @return 위도,경도 Array
     */
    private double[] setMapLocation() {

        Geocoder geocoder = new Geocoder(mContext);
        String address = "";
        //주소 입력
        if (mRestaurantDetail.getRestaurant() != null) {
            address = mRestaurantDetail.getRestaurant().get(0).getRestaurantAddress();
        }

        double lat = 0 , lon = 0;

        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(address,10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constants.MAP_ERROR_TAG,"입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Log.e(Constants.MAP_ERROR_TAG,"해당되는 주소 정보는 없습니다");
            } else {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
            }
        }

        return new double[]{lat , lon};
    }

    /**
     * 네이버지도 설정
     */
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        double[] latlon = setMapLocation();
        //위도와 경도 을 지정
        LatLng location = new LatLng(latlon[0], latlon[1]);
        Marker marker = new Marker();
        marker.setPosition(location);
        marker.setMap(naverMap);
        marker.setIcon(OverlayImage.fromResource(R.drawable.icon_restaurant_spot));

        // 카메라 위치와 줌 조절(숫자가 클수록 확대)
        CameraPosition cameraPosition = new CameraPosition(location, 17);
        naverMap.setCameraPosition(cameraPosition);

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
}
