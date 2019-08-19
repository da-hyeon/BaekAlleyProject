package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.databinding.ItemReviewBinding;

import java.util.ArrayList;

public class RestaurantReviewListAdapter extends RecyclerView.Adapter<RestaurantReviewListAdapter.RestaurantMenuListViewHolder> {

    private ArrayList<Review> mRestaurantReviewList;
    private Context mContext;

    class RestaurantMenuListViewHolder extends RecyclerView.ViewHolder {

        private ItemReviewBinding binding;

        RestaurantMenuListViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RestaurantReviewListAdapter(ArrayList<Review> mRestaurantReviewList, Context mContext) {
        this.mRestaurantReviewList = mRestaurantReviewList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantReviewListAdapter.RestaurantMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantReviewListAdapter.RestaurantMenuListViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantReviewListAdapter.RestaurantMenuListViewHolder holder, int position) {
        //holder.binding.tvMenuName.setText(mRestaurantReviewList.get(position).getMenuName());
       // holder.binding.tvMenuPrice.setText(mRestaurantReviewList.get(position).getMenuPrice());
    }

    @Override
    public int getItemCount() {
        return (mRestaurantReviewList != null ? mRestaurantReviewList.size() : 0);
    }

}