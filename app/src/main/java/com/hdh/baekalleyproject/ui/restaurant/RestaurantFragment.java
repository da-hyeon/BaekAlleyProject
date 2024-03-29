package com.hdh.baekalleyproject.ui.restaurant;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.FragmentRestaurantBinding;
import com.hdh.baekalleyproject.ui.base.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends BaseFragment implements RestaurantContract.View {

    private RestaurantContract.Presenter mPresenter;
    private FragmentRestaurantBinding mBinding;
    private RestaurantPresenter.ScrollEvent mScrollEvent;

    public RestaurantFragment() {
    }

    @SuppressLint("ValidFragment")
    public RestaurantFragment(RestaurantPresenter.ScrollEvent mScrollEvent) {
        this.mScrollEvent = mScrollEvent;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant, container, false);
        mPresenter = new RestaurantPresenter(this, getContext(), getActivity(), getFragmentManager(), mBinding.rvRestaurantList, mScrollEvent);

        initData();

        //필터클릭
        mBinding.btFilter.setOnClickListener(v ->
                mPresenter.clickFilter()
        );

        //찾기클릭
        mBinding.btSearch.setOnClickListener(v ->
                mPresenter.clickSearch()
        );

        return mBinding.getRoot();
    }

    /**
     * 데이터 생성 및 초기화
     */
    private void initData() {
        mPresenter.setAdvertisementView(mBinding.vpEvent, mBinding.tlDots);
        mBinding.rvRestaurantList.setNestedScrollingEnabled(false);
        mPresenter.dataLoadMore(mBinding.nsView);
    }

    /**
     * 재진입
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setRestaurantView();
    }

    /**
     * 일반 액티비티 이동
     *
     * @param intent intent
     */
    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 옵션 액티비티 이동
     *
     * @param intent intent
     */
    @Override
    public void moveOptionActivity(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }

    @Override
    public void moveOptionActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }

    @Override
    public void showLoading() {
        mBinding.pbLoading.setVisibility(View.VISIBLE);
        //mBinding.pbLoading.setIndeterminate(true);
        mBinding.nsView.setVisibility(View.GONE);
    }

    @Override
    public void showListLoading() {
        mBinding.pbListLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBinding.pbLoading.setVisibility(View.GONE);
        mBinding.nsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListLoading() {
        mBinding.pbListLoading.setVisibility(View.INVISIBLE);
    }
}
