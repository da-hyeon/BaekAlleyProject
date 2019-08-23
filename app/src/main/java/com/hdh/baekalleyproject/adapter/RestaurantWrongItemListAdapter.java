package com.hdh.baekalleyproject.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.WrongItem;
import com.hdh.baekalleyproject.databinding.ItemWrongBinding;

import java.util.ArrayList;

public class RestaurantWrongItemListAdapter extends RecyclerView.Adapter<RestaurantWrongItemListAdapter.RestaurantWrongItemListViewHolder> {

    private ArrayList<WrongItem> mRestaurantWrongItemList;
    private Context mContext;

    class RestaurantWrongItemListViewHolder extends RecyclerView.ViewHolder {

        private ItemWrongBinding binding;

        RestaurantWrongItemListViewHolder(ItemWrongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.clView.setOnClickListener(v-> {
                int position = getAdapterPosition();
                mRestaurantWrongItemList.get(position).setCheckBoxStatus(!binding.cbCheck.isChecked());
                notifyItemChanged(position);
            });
        }
    }

    public RestaurantWrongItemListAdapter(ArrayList<WrongItem> mRestaurantWrongItemList, Context mContext) {
        this.mRestaurantWrongItemList = mRestaurantWrongItemList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantWrongItemListAdapter.RestaurantWrongItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantWrongItemListAdapter.RestaurantWrongItemListViewHolder(ItemWrongBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @Override
    public void onBindViewHolder(RestaurantWrongItemListAdapter.RestaurantWrongItemListViewHolder holder, int position) {
        if (mRestaurantWrongItemList.get(position).isCheckBoxStatus()){
            holder.binding.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            holder.binding.tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.wrongInfoTextDefaultColor));
        }
        holder.binding.tvContent.setText(mRestaurantWrongItemList.get(position).getWrongTitle());
        holder.binding.cbCheck.setChecked(mRestaurantWrongItemList.get(position).isCheckBoxStatus());
    }

    @Override
    public int getItemCount() {
        return (mRestaurantWrongItemList != null ? mRestaurantWrongItemList.size() : 0);
    }

}