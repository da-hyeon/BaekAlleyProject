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
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.Review;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.databinding.ItemReviewBinding;
import com.hdh.baekalleyproject.ui.login.LoginActivity;
import com.hdh.baekalleyproject.ui.review_detail.ReviewDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

            //리뷰 클릭
            binding.llReview.setOnClickListener(v->{
                int position = getAdapterPosition();
                Intent intent = new Intent(mContext, ReviewDetailActivity.class);
                //리뷰객체 넘기기
                intent.putExtra(Constants.REVIEW_DATA, mRestaurantReviewList.get(position));
                mContext.startActivity(intent);
            });

            //좋아요 클릭
            binding.vReviewLike.setOnClickListener(v -> {
                int position = getAdapterPosition();
                //로그인일 때
                if (mUserInformation != null) {
                   // Toast.makeText(mContext, mRestaurantReviewList.get(position).getReviewID() + "번 , 글쓴이 " + mRestaurantReviewList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                    Call<Integer> requestRegistrationReviewLike = MyApplication
                            .getRestAdapter()
                            .requestRegistrationReviewLike(
                                    mRestaurantReviewList.get(position).getRestaurantID(),
                                    mUserInformation.getId(),
                                    mRestaurantReviewList.get(position).getReviewID());

                    requestRegistrationReviewLike.enqueue(new Callback<Integer>() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                            if (response.code() == 200) {

                                binding.cbLikeMark.setChecked(!binding.cbLikeMark.isChecked());
                                if (binding.cbLikeMark.isChecked()) {
                                    binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                                    binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
                                } else {
                                    binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                                    binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                                }
                                if (response.body() > 0) {
                                    binding.tvLikeCount.setText(response.body() + "개");
                                } else {
                                    binding.tvLikeCount.setText("");
                                }
                            } else {
                                //mView.showFailDialog("실패" , "데이터 로딩 실패");
                                Log.d("실패", "데이터 로딩 실패");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                            Log.d("error", t.getMessage());
                            Log.d("error", t.getLocalizedMessage());
                        }
                    });


                } else {
                    Toast.makeText(mContext, "로그인 해주세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                }
            });
        }
    }

    public RestaurantReviewListAdapter(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;

        IMG_SIZE = Math.round(36 * mContext.getResources().getDisplayMetrics().density);
        mUserInformation = MyApplication.getUserInformationInstance();
    }

    @Override
    public RestaurantReviewListAdapter.RestaurantReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantReviewListAdapter.RestaurantReviewListViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantReviewListAdapter.RestaurantReviewListViewHolder holder, int position) {
        holder.binding.tvUserName.setText(mRestaurantReviewList.get(position).getUserName());
        holder.binding.tvReviewMenu.setText(mRestaurantReviewList.get(position).getReviewMenu());
        holder.binding.tvReviewTitle.setText(mRestaurantReviewList.get(position).getReviewTitle());
        holder.binding.tvReviewContent.setText(mRestaurantReviewList.get(position).getReviewContent());
        if (mRestaurantReviewList.get(position).getReviewLikeCount() > 0) {
            holder.binding.tvLikeCount.setText(mRestaurantReviewList.get(position).getReviewLikeCount() + "개");
        } else {
            holder.binding.tvLikeCount.setText("");
        }

        holder.binding.cbLikeMark.setChecked(mRestaurantReviewList.get(position).isLikeClickStatus());
        if (mRestaurantReviewList.get(position).isLikeClickStatus()) {
            holder.binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
            holder.binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
        } else {
            holder.binding.tvLikeText.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
            holder.binding.tvLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
        }


        //등록된 유저의 이미지가 없을 경우
        if (mRestaurantReviewList.get(position).getUserImageURL() == null) {

        } else {
            Glide.with(mContext)
                    .load(mRestaurantReviewList.get(position).getUserImageURL())
                    .apply(new RequestOptions().override(IMG_SIZE, IMG_SIZE).centerCrop().circleCrop())
                    .into(holder.binding.ivUserImage);
        }

        switch (mRestaurantReviewList.get(position).getRevivewTasteType()) {
            case 1:
                holder.binding.ivTaste.setImageResource(R.drawable.icon_great);
                break;
            case 2:
                holder.binding.ivTaste.setImageResource(R.drawable.icon_good);
                break;
            case 3:
                holder.binding.ivTaste.setImageResource(R.drawable.icon_bad);
                break;
        }

        if (mRestaurantReviewList.get(position).getCommentCount() > 0){
            holder.binding.tvComment.setText("댓글 " + mRestaurantReviewList.get(position).getCommentCount()+"개");
        } else {
            holder.binding.tvComment.setText("댓글달기");
        }

        holder.binding.tvElapsedTime.setText(getElapsedTime(mRestaurantReviewList.get(position).getReviewRegistrationDate()));

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

    /**
     * 날짜 차이 계산
     *
     * @param date 등록된 날짜
     * @return 날짜 차이
     */
    private String getElapsedTime(String date) {
        String elapsedTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();
        String time = formatter.format(currentTime);
        Date registrationDate = null;

        try {
            registrationDate = formatter.parse(date);
            currentTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
        long diff = 0;
        if (registrationDate != null) {
            diff = registrationDate.getTime() - currentTime.getTime();
        } else {
            return "error";
        }
        long diffDays = diff / (60 * 1000);

        diffDays *= -1;
        if (diffDays == 0) {
            elapsedTime = "방금 전";
        } else if (diffDays < 60) {
            elapsedTime = diffDays + "분 전";
        } else if (diffDays < 1440) {
            elapsedTime = (diffDays / 60) + "시간 전";
        } else if (diffDays < 43200) {
            elapsedTime = (diffDays / 1440) + "일 전";
        } else if (diffDays < 518400) {
            elapsedTime = (diffDays / 43200) + "달 전";
        } else {
            elapsedTime = (diffDays / 518400) + "년 전";
        }
//        if (diffDays == 0) {
//            elapsedTime = "오늘";
//        } else if (diffDays < 7) {
//            elapsedTime = diffDays + "일 전";
//        } else if (diffDays < 31) {
//            elapsedTime = (diffDays / 7) + "주 전";
//        } else if (diffDays < 365) {
//            elapsedTime = (diffDays % 30) + "달 전";
//        } else {
//            elapsedTime = (diffDays % 365) + "년 전";
//        }

        return elapsedTime;
    }
}