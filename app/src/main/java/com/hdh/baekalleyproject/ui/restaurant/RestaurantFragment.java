package com.hdh.baekalleyproject.ui.restaurant;


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
public class RestaurantFragment extends BaseFragment implements RestaurantContract.View{

    private RestaurantContract.Presenter mPresenter;
    private FragmentRestaurantBinding mBinding;

    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_restaurant, container, false);
        mPresenter = new RestaurantPresenter(this, getContext() , getFragmentManager());

        initData();

        mBinding.btFilter.setOnClickListener(v->
                mPresenter.clickFilter()
        );

        return mBinding.getRoot();
    }

    private void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setView(mBinding.rvRestaurantList , mBinding.vpEvent , mBinding.tlDots);
    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }
}
