package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.data.util.CustomRoundedCornersTransformation;
import com.hdh.baekalleyproject.databinding.ItemRestaurantBinding;
import com.hdh.baekalleyproject.ui.restaurant_detail.RestaurantDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    private ArrayList<Restaurant> mRestaurantList;
    private Context mContext;
    private final int IMG_SIZE;
    private final int IMG_CORNER_RADIUS;
    private final String mTag;

    private Realm mRealm;
    private RecentSearchTerm mRecentSearchTermList;
    private String currentText;

    class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantBinding binding;

        RestaurantListViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.llRestaurant.setOnClickListener(v1 -> {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra(Constants.RESTAURANT_ID, mRestaurantList.get(getAdapterPosition()).getRestaurantID());
                mContext.startActivity(intent);

                if (mTag.equals(Constants.FILTER_ADAPTER)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                    Date currentTime = new Date();
                    String time = formatter.format(currentTime);
                    try {
                        currentTime = formatter.parse(time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    saveFilter(currentTime);
                    //mRecentSearchTermList.getRecentSearchTermList().add(new RecentSearchTerm(currentText, currentTime));
                    //saveFilter(mRecentSearchTermList);
                }
            });
        }
    }

    public RestaurantListAdapter(Context mContext, String mTag) {
        this.mContext = mContext;
        this.mTag = mTag;
        IMG_CORNER_RADIUS = Math.round(8 * mContext.getResources().getDisplayMetrics().density);
        IMG_SIZE = Math.round(156 * mContext.getResources().getDisplayMetrics().density);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantListViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mRestaurantList.get(position).getRestaurantImageURL())
                .apply(new RequestOptions().transform(new MultiTransformation(new CenterCrop(), new CustomRoundedCornersTransformation(mContext, IMG_CORNER_RADIUS, 0, CustomRoundedCornersTransformation.CornerType.ALL))).override(IMG_SIZE, IMG_SIZE).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(holder.binding.ivRestaurantImage);

        //holder.binding.ivRestaurantImage.setClipToOutline(true);

        holder.binding.tvRestaurantRank.setText((position + 1) + ".");
        holder.binding.tvRestaurantName.setText(" " + mRestaurantList.get(position).getRestaurantName());
        holder.binding.tvRestaurantAlley.setText(mRestaurantList.get(position).getRestaurantAlley());
        holder.binding.tvRestaurantNOV.setText(" " + mRestaurantList.get(position).getRestaurantNumberOfView());
        holder.binding.tvRestaurantReviewNOV.setText(" " + mRestaurantList.get(position).getRestaurantNumberOfReview());
        holder.binding.tvRestaurantRepFood.setText("대표메뉴 : " + mRestaurantList.get(position).getRestaurantRepFood());

        holder.binding.tvRestaurantStar.setText(mRestaurantList.get(position).getRestaurantRank());

        if (mRestaurantList.get(position).getRestaurantRankStatus().equals(Constants.RANK_STATUS_CONFIRMATION)){
            holder.binding.tvRestaurantStar.setVisibility(View.VISIBLE);
            holder.binding.tvRestaurantStar.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else if (mRestaurantList.get(position).getRestaurantRankStatus().equals(Constants.RANK_STATUS_DATE_NOT_SET)){
            holder.binding.tvRestaurantStar.setVisibility(View.VISIBLE);
            holder.binding.tvRestaurantStar.setTextColor(ContextCompat.getColor(mContext, R.color.foodTextDefaultColor));
        } if (mRestaurantList.get(position).getRestaurantRankStatus().equals(Constants.RANK_STATUS_EVALUATION_NOT_DECIDED)){
            holder.binding.tvRestaurantStar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mRestaurantList != null ? mRestaurantList.size() : 0;
    }

    public void setRestaurantList(ArrayList<Restaurant> mRestaurantList) {
        this.mRestaurantList = mRestaurantList;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    /**
     * 검색어 저장하기
     */
    private void saveFilter(Date currentTime) {
        mRealm.beginTransaction();
        Number number = mRealm.where(RecentSearchTerm.class).max("id");
        long id = number == null ? 0 : number.longValue() + 1;

        mRecentSearchTermList = mRealm.createObject(RecentSearchTerm.class , (int)id);
        mRecentSearchTermList.setRecentSearchTerm(currentText);
        mRecentSearchTermList.setDate(currentTime);
        mRealm.commitTransaction();
    }
}
