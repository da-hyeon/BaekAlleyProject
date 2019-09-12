package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.data.model.RestaurantImage;
import com.hdh.baekalleyproject.data.util.CustomRoundedCornersTransformation;
import com.hdh.baekalleyproject.databinding.ItemRestaurantDetailImgBinding;
import com.hdh.baekalleyproject.ui.photo.PhotoActivity;

import java.util.ArrayList;

public class RestaurantImageListAdapter extends RecyclerView.Adapter<RestaurantImageListAdapter.RestaurantImageListViewHolder> {

    private ArrayList<RestaurantImage> mRestaurantImageList;
    private Context mContext;
    private final int IMG_SIZE;
    private final int IMG_CORNER_RADIUS;


    class RestaurantImageListViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantDetailImgBinding binding;

        RestaurantImageListViewHolder(ItemRestaurantDetailImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.ivRestaurantImage.setOnClickListener(v->{
                int restaurantImageListSize = mRestaurantImageList.size();
                String[] imageURLArray = new String[restaurantImageListSize];

                for(int i = 0; i < restaurantImageListSize; i++){
                    imageURLArray[i] = mRestaurantImageList.get(i).getImageURL();
                }
                Intent intent = new Intent(mContext , PhotoActivity.class);
                intent.putExtra(Constants.RESTAURANT_IMAGES,imageURLArray);
                intent.putExtra(Constants.RESTAURANT_IMAGE_CLICK_POSITION , getAdapterPosition());
                mContext.startActivity(intent);
            });
        }
    }

    public RestaurantImageListAdapter(Context mContext) {
        this.mContext = mContext;
        IMG_CORNER_RADIUS = Math.round(8 * mContext.getResources().getDisplayMetrics().density);
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
                .load(mRestaurantImageList.get(position).getImageURL())
                .apply(new RequestOptions().transform(new MultiTransformation(new CenterCrop(), new CustomRoundedCornersTransformation(mContext, IMG_CORNER_RADIUS, 0, CustomRoundedCornersTransformation.CornerType.ALL))).override(IMG_SIZE , IMG_SIZE).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(holder.binding.ivRestaurantImage);
        //holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantTime());
//        holder.binding.tvRestaurantRepFood.setText(mRestaurantList.get(position).getRestaurantRepFood());
    }

    @Override
    public int getItemCount() {
        return mRestaurantImageList != null ? mRestaurantImageList.size() : 0;
    }

    public void setRestaurantImageList(ArrayList<RestaurantImage> mRestaurantImageList) {
        this.mRestaurantImageList = mRestaurantImageList;
    }
}