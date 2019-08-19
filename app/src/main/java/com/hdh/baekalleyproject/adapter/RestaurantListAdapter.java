package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.data.util.CustomRoundedCornersTransformation;
import com.hdh.baekalleyproject.databinding.ItemRestaurantBinding;
import com.hdh.baekalleyproject.ui.restaurant_detail.RestaurantDetailActivity;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    private ArrayList<Restaurant> mRestaurantList;
    private Context mContext;
    private final int IMG_SIZE;

    class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantBinding binding;

        RestaurantListViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RestaurantListAdapter(ArrayList<Restaurant> mRestaurantList, Context mContext) {
        this.mRestaurantList = mRestaurantList;
        this.mContext = mContext;
        IMG_SIZE = Math.round(8 * mContext.getResources().getDisplayMetrics().density);
    }

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantListViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mRestaurantList.get(position).getRestaurantImageURL())
                .apply(new RequestOptions().transform(new MultiTransformation(new CenterCrop(), new CustomRoundedCornersTransformation(mContext, IMG_SIZE, 0, CustomRoundedCornersTransformation.CornerType.ALL))))
                .into(holder.binding.ivRestaurantImage);

        //holder.binding.ivRestaurantImage.setClipToOutline(true);

        holder.binding.tvRestaurantRank.setText((position + 1) + ".");
        holder.binding.tvRestaurantName.setText(" "+mRestaurantList.get(position).getRestaurantName());
        holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantAlley());
        holder.binding.tvRestaurantNOV.setText(" "+mRestaurantList.get(position).getRestaurantNOV());
        holder.binding.tvRestaurantReviewNOV.setText(" "+mRestaurantList.get(position).getRestaurantReviewNOV());

        holder.binding.llRestaurant.setOnClickListener(v1->{
            Intent intent = new Intent(mContext , RestaurantDetailActivity.class);
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return (mRestaurantList != null ? mRestaurantList.size() : 0);
    }


}
