package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.data.model.RestaurantMenu;
import com.hdh.baekalleyproject.databinding.ItemRestaurantDetailMenuBinding;

import java.util.ArrayList;

public class RestaurantMenuListAdapter extends RecyclerView.Adapter<RestaurantMenuListAdapter.RestaurantMenuListViewHolder> {

    private ArrayList<RestaurantMenu> mRestaurantMenuList;
    private Context mContext;

    class RestaurantMenuListViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantDetailMenuBinding binding;

        RestaurantMenuListViewHolder(ItemRestaurantDetailMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RestaurantMenuListAdapter(ArrayList<RestaurantMenu> mRestaurantMenuList, Context mContext) {
        this.mRestaurantMenuList = mRestaurantMenuList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantMenuListAdapter.RestaurantMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantMenuListAdapter.RestaurantMenuListViewHolder(ItemRestaurantDetailMenuBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantMenuListAdapter.RestaurantMenuListViewHolder holder, int position) {
        holder.binding.tvMenuName.setText(mRestaurantMenuList.get(position).getMenuName());
        holder.binding.tvMenuPrice.setText(mRestaurantMenuList.get(position).getMenuPrice());
    }

    @Override
    public int getItemCount() {
        return mRestaurantMenuList != null ? mRestaurantMenuList.size() : 0;
    }

}