package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.databinding.ItemRecentSearchesBinding;

import java.util.ArrayList;

public class RecentSearchTermListAdapter extends RecyclerView.Adapter<RecentSearchTermListAdapter.RestaurantImageListViewHolder> {

    private ArrayList<RecentSearchTerm> mRecentSearchTermList;
    private Context mContext;

    class RestaurantImageListViewHolder extends RecyclerView.ViewHolder {

        private ItemRecentSearchesBinding binding;

        RestaurantImageListViewHolder(ItemRecentSearchesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RecentSearchTermListAdapter(ArrayList<RecentSearchTerm> mRestaurantImageList, Context mContext) {
        this.mRecentSearchTermList = mRestaurantImageList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantImageListViewHolder(ItemRecentSearchesBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantImageListViewHolder holder, int position) {

        holder.binding.tvSearchTerm.setText(mRecentSearchTermList.get(position).getRecentSearchTerm());
        holder.binding.tvDateSearched.setText(mRecentSearchTermList.get(position).getDate());
        //holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantTime());
//        holder.binding.tvRestaurantRepFood.setText(mRestaurantList.get(position).getRestaurantRepFood());
    }

    @Override
    public int getItemCount() {
        return (mRecentSearchTermList != null ? mRecentSearchTermList.size() : 0);
    }


}