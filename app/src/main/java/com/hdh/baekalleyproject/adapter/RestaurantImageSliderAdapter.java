package com.hdh.baekalleyproject.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ItemRestaurantImgBinding;

import java.util.ArrayList;

public class RestaurantImageSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> mImageArrayList;

    public RestaurantImageSliderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImageArrayList != null ? mImageArrayList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ItemRestaurantImgBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_restaurant_img, container, false);
        mBinding.getRoot().setTag(position);

        Glide.with(mContext)
                .load(mImageArrayList.get(position))
                .apply(new RequestOptions().centerCrop().override(500,500).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(mBinding.pvPhoto);

        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

    public void setmImageArrayList(ArrayList<String> mImageArrayList) {
        this.mImageArrayList = mImageArrayList;
    }
}
