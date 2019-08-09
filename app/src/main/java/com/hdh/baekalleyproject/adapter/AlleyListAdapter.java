package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.data.model.Alley;
import com.hdh.baekalleyproject.databinding.ItemFilterAlleyBinding;

import java.util.ArrayList;

public class AlleyListAdapter extends RecyclerView.Adapter<AlleyListAdapter.RestaurantImageListViewHolder> {

    private ArrayList<Alley> mAlleyList;
    private Context mContext;

    class RestaurantImageListViewHolder extends RecyclerView.ViewHolder {

        private ItemFilterAlleyBinding binding;

        RestaurantImageListViewHolder(ItemFilterAlleyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public AlleyListAdapter(ArrayList<Alley> mAlleyList, Context mContext) {
        this.mAlleyList = mAlleyList;
        this.mContext = mContext;
    }

    @Override
    public RestaurantImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantImageListViewHolder(ItemFilterAlleyBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantImageListViewHolder holder, int position) {

        String[] alley = mAlleyList.get(position).getAlleyName().split(" ");
        holder.binding.tvArea.setText(alley[0]);
        if (alley.length == 3){
            holder.binding.tvAlley.setText(alley[1]+ "\n" + alley[2]);
        } else {
            holder.binding.tvAlley.setText(alley[1]);
        }

        holder.binding.vAlley.setOnClickListener(v1->{

        });
        //holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantTime());
//        holder.binding.tvRestaurantRepFood.setText(mRestaurantList.get(position).getRestaurantRepFood());
    }

    @Override
    public int getItemCount() {
        return (mAlleyList != null ? mAlleyList.size() : 0);
    }
}