package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.data.model.RestaurantImage;
import com.hdh.baekalleyproject.databinding.ItemRestaurantDetailImgBinding;

import java.util.ArrayList;

public class RestaurantImageListAdapter extends RecyclerView.Adapter<RestaurantImageListAdapter.RestaurantImageListViewHolder> {

    private ArrayList<RestaurantImage> mRestaurantImageList;
    private Context mContext;
    private final int IMG_SIZE;

    class RestaurantImageListViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantDetailImgBinding binding;

        RestaurantImageListViewHolder(ItemRestaurantDetailImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RestaurantImageListAdapter(ArrayList<RestaurantImage> mRestaurantImageList, Context mContext) {
        this.mRestaurantImageList = mRestaurantImageList;
        this.mContext = mContext;
        IMG_SIZE = Math.round(164 * mContext.getResources().getDisplayMetrics().density);
    }

    @Override
    public RestaurantImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantImageListViewHolder(ItemRestaurantDetailImgBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantImageListViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mRestaurantImageList.get(position).getRestaurantImageURL())
                .apply(new RequestOptions().centerCrop())
                .into(holder.binding.ivRestaurantImage);
        //holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantTime());
//        holder.binding.tvRestaurantRepFood.setText(mRestaurantList.get(position).getRestaurantRepFood());
    }

    @Override
    public int getItemCount() {
        return (mRestaurantImageList != null ? mRestaurantImageList.size() : 0);
    }


}