package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.databinding.ItemCommentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RestaurantReviewCommentListAdapter extends RecyclerView.Adapter<RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder> {

    private ArrayList<Review> mRestaurantReviewCommentList;
    private Context mContext;
    private Activity mActivity;
    private final int IMG_SIZE;

    private UserInformation mUserInformation;

    class RestaurantReviewCommentListViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentBinding binding;

        RestaurantReviewCommentListViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RestaurantReviewCommentListAdapter(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;

        IMG_SIZE = Math.round(36 * mContext.getResources().getDisplayMetrics().density);
        mUserInformation = MyApplication.getUserInformationInstance();
    }

    @Override
    public RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder holder, int position) {

        //등록된 유저의 이미지가 없을 경우
        if (mRestaurantReviewCommentList.get(position).getUserImageURL() == null) {

        } else {
            Glide.with(mContext)
                    .load(mRestaurantReviewCommentList.get(position).getUserImageURL())
                    .apply(new RequestOptions().override(IMG_SIZE, IMG_SIZE).centerCrop().circleCrop())
                    .into(holder.binding.ivUserImage);
        }

        holder.binding.tvUserName.setText(mRestaurantReviewCommentList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return mRestaurantReviewCommentList != null ? mRestaurantReviewCommentList.size() : 0;
    }

    public void setRestaurantReviewCommentList(ArrayList<Review> mRestaurantReviewCommentList) {
        this.mRestaurantReviewCommentList = mRestaurantReviewCommentList;
    }

    /**
     * 날짜 차이 계산
     *
     * @param date 등록된 날짜
     * @return 날짜 차이
     */
    private String getElapsedTime(String date) {
        String elapsedTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();
        String time = formatter.format(currentTime);
        Date registrationDate = null;

        try {
            registrationDate = formatter.parse(date);
            currentTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
        long diff = 0;
        if (registrationDate != null) {
            diff = registrationDate.getTime() - currentTime.getTime();
        } else {
            return "error";
        }
        long diffDays = diff / (60 * 1000);

        diffDays *= -1;
        if (diffDays == 0) {
            elapsedTime = "방금 전";
        } else if (diffDays < 60) {
            elapsedTime = diffDays + "분 전";
        } else if (diffDays < 1440) {
            elapsedTime = (diffDays / 60) + "시간 전";
        } else if (diffDays < 43200) {
            elapsedTime = (diffDays / 1440) + "일 전";
        } else if (diffDays < 518400) {
            elapsedTime = (diffDays / 43200) + "달 전";
        } else {
            elapsedTime = (diffDays / 518400) + "년 전";
        }
//        if (diffDays == 0) {
//            elapsedTime = "오늘";
//        } else if (diffDays < 7) {
//            elapsedTime = diffDays + "일 전";
//        } else if (diffDays < 31) {
//            elapsedTime = (diffDays / 7) + "주 전";
//        } else if (diffDays < 365) {
//            elapsedTime = (diffDays % 30) + "달 전";
//        } else {
//            elapsedTime = (diffDays % 365) + "년 전";
//        }

        return elapsedTime;
    }
}