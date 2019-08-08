package com.hdh.baekalleyproject.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.Event;
import com.hdh.baekalleyproject.databinding.EventSliderBinding;

import java.util.ArrayList;

public class EventImageSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Event> mEventArrayList;
    private FragmentManager mFragmentManager;

    public EventImageSliderAdapter(Context mContext, FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getCount() {
        return mEventArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        EventSliderBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.event_slider, container, false);
        mBinding.getRoot().setTag(position);

        if (mEventArrayList.size() > 0) {
            mBinding.ivEventImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.mainbanner_1));
        }

        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

    public void setmEventArrayList(ArrayList<Event> mEventArrayList) {
        this.mEventArrayList = mEventArrayList;
    }
}
