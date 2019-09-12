package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.databinding.ItemReviewBinding;
import com.hdh.baekalleyproject.ui.login.LoginActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReviewListAdapter extends RecyclerView.Adapter<RestaurantReviewListAdapter.RestaurantReviewListViewHolder> {

    private ArrayList<Review> mRestaurantReviewList;
    private Context mContext;
    private Activity mActivity;
    private final int IMG_SIZE;

    private UserInformation mUserInformation;

    class RestaurantReviewListViewHolder extends RecyclerView.ViewHolder {

        private ItemReviewBinding binding;

        RestaurantReviewListViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.tvReviewMenu.setSelected(true);

            binding.vReviewLike.setOnClickListener(v->{
                int position = getAdapterPosition();
                //로그인일 때
                if (mUserInformation != null) {
                    Toast.makeText(mContext, mRestaurantReviewList.get(position).getReviewID()+"번 , 글쓴이 " + mRestaurantReviewList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                    Call<String> requestRegistrationReviewLike = MyApplication
                            .getRestAdapter()
                            .requestRegistrationReviewLike(
                                    mRestaurantReviewList.get(position).getRestaurantID(),
                                    mUserInformation.getId(),
                                    mRestaurantReviewList.get(position).getReviewID());

                    requestRegistrationReviewLike.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.code() == 200) {

                                binding.cbLikeMark.setChecked(!binding.cbLikeMark.isChecked());
                                if (binding.cbLikeMark.isChecked()) {
                                    binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                                    binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                                } else {
                                    binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                                    binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                                }
                                binding.tvLikeCount.setText(response.body()+"개");
                            } else {
                                //mView.showFailDialog("실패" , "데이터 로딩 실패");
                                Log.d("실패", "데이터 로딩 실패");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Log.d("error", t.getMessage());
                            Log.d("error", t.getLocalizedMessage());
                        }
                    });


                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    mActivity.finish();
                }
            });
        }
    }

    public RestaurantReviewListAdapter( Context mContext , Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;

        IMG_SIZE = Math.round(36 * mContext.getResources().getDisplayMetrics().density);
        mUserInformation = MyApplication.getUserInformationInstance();
    }

    @Override
    public RestaurantReviewListAdapter.RestaurantReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantReviewListAdapter.RestaurantReviewListViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantReviewListAdapter.RestaurantReviewListViewHolder holder, int position) {
        holder.binding.tvUserName.setText(mRestaurantReviewList.get(position).getUserName());
        holder.binding.tvReviewMenu.setText(mRestaurantReviewList.get(position).getReviewMenu());
        holder.binding.tvReviewTitle.setText(mRestaurantReviewList.get(position).getReviewTitle());
        holder.binding.tvReviewContent.setText(mRestaurantReviewList.get(position).getReviewContent());
        holder.binding.tvLikeCount.setText(mRestaurantReviewList.get(position).getReviewLikeCount()+"개");

        holder.binding.cbLikeMark.setChecked(mRestaurantReviewList.get(position).isWillGoClickStatus());
        if (mRestaurantReviewList.get(position).isWillGoClickStatus()) {
            holder.binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
            holder.binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
        } else {
            holder.binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
            holder.binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
        }


        if (mRestaurantReviewList.get(position).getUserImageURL() == null){

        } else {
            Glide.with(mContext)
                    .load(mRestaurantReviewList.get(position).getUserImageURL())
                    .apply(new RequestOptions().override(IMG_SIZE,IMG_SIZE).centerCrop().circleCrop())
                    .into(holder.binding.ivUserImage);
        }

        //holder.binding.tvMenuName.setText(mRestaurantReviewList.get(position).getMenuName());
       // holder.binding.tvMenuPrice.setText(mRestaurantReviewList.get(position).getMenuPrice());
    }

    @Override
    public int getItemCount() {
        return mRestaurantReviewList != null ? mRestaurantReviewList.size() : 0;
    }

    public void setRestaurantReviewList(ArrayList<Review> mRestaurantReviewList) {
        this.mRestaurantReviewList = mRestaurantReviewList;
    }
}