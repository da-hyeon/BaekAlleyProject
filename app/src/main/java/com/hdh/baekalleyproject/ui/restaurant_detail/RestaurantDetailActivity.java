package com.hdh.baekalleyproject.ui.restaurant_detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityRestaurantDetailBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

public class RestaurantDetailActivity extends BaseActivity implements RestaurantDetailContract.View, OnMapReadyCallback {

    private RestaurantDetailContract.Presenter mPresenter;
    private ActivityRestaurantDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);
        mPresenter = new RestaurantDetailPresenter(this, this, this);
        initData();

        //뒤로가기 클릭
        mBinding.vBack.setOnClickListener(v ->
                mPresenter.clickDismiss()
        );

        //가야쥬 클릭
        mBinding.vGo.setOnClickListener(v -> {

        });

        //리뷰쓰기 클릭
        mBinding.vWriteReview.setOnClickListener(v -> {

        });

        //맵 클릭
        mBinding.mvMap.setOnClickListener(v -> {

        });

        //전화하기 클릭
        mBinding.vCall.setOnClickListener(v -> {

        });

        //주소복사 클릭
        mBinding.vAddressCopy.setOnClickListener(v -> {

        });

        //잘못된 정보알림 클릭
        mBinding.vWrongInfo.setOnClickListener(v -> {

        });

        //맛있네유 클릭
        mBinding.llGreat.setOnClickListener(v -> {

        });

        //괜찮네유 클릭
        mBinding.llGood.setOnClickListener(v -> {

        });

        //별론데유 클릭
        mBinding.llBad.setOnClickListener(v -> {

        });
    }

    /**
     * 데이터 생성 및 초기화
     */
    private void initData() {
        mPresenter.setView(mBinding.rvRestaurantImageList, mBinding.rvRestaurantMenuList, mBinding.rvReviewList);

        //fragment로 진행 시 밑의 부분 주석 해제
       /* MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }*/

        mBinding.mvMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        double[] latlon = mPresenter.setMapLocation();
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
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT , true);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setLogoClickEnabled(false);
        uiSettings.setScrollGesturesEnabled(false);
        uiSettings.setZoomGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);


    }

    @Override
    public void onBackPressed() {
        mPresenter.clickDismiss();
    }

    @Override
    public void changeGo(boolean state) {
        if (state) {
            mBinding.ivGo.setImageResource(R.drawable.icon_likeit_on);
            mBinding.tvGoText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            mBinding.ivGo.setImageResource(R.drawable.icon_likeit);
            mBinding.tvGoText.setTextColor(ContextCompat.getColor(this, R.color.goTextDefaultColor));
        }
    }
}
