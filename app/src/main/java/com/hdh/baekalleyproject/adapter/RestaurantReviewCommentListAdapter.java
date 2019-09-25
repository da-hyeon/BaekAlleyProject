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
import com.hdh.baekalleyproject.data.model.ReviewComment;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.databinding.ItemCommentBinding;
import com.hdh.baekalleyproject.ui.login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReviewCommentListAdapter extends RecyclerView.Adapter<RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder> {

    private ArrayList<ReviewComment> mRestaurantReviewCommentList;
    private Context mContext;
    private Activity mActivity;
    private final int IMG_SIZE;

    private UserInformation mUserInformation;

    private String mRestaurantID;
    private String mReviewID;

    class RestaurantReviewCommentListViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentBinding binding;

        RestaurantReviewCommentListViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//37 https://assets9.lottiefiles.com/datafiles/ZfX3q0fK9I8mI6O/data.json
            //좋아요 클릭
            binding.vLike.setOnClickListener(v -> {
                int position = getAdapterPosition();
                //로그인일 때
                if (mUserInformation != null) {
                    // Toast.makeText(mContext, mRestaurantReviewList.get(position).getReviewID() + "번 , 글쓴이 " + mRestaurantReviewList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                    Call<Integer> requestRegistrationReviewCommentLike = MyApplication
                            .getRestAdapter()
                            .requestRegistrationReviewCommentLike(
                                    mRestaurantID,
                                    mReviewID ,
                                    mUserInformation.getId(),
                                    mRestaurantReviewCommentList.get(position).getId());

                    requestRegistrationReviewCommentLike.enqueue(new Callback<Integer>() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                            if (response.code() == 200) {

                                binding.cbLike.setChecked(!binding.cbLike.isChecked());

//                                binding.laLikeAnimation.setAnimation("songLike.json");
//                                toggleSongLikeAnimButton(binding.laLikeAnimation, binding.cbLike.isChecked());

                                if (binding.cbLike.isChecked()) {
                                    binding.tvCountOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));

                                } else {
                                    binding.tvCountOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
                                }

                                binding.tvCountOfLike.setText(response.body()+"");
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

    public RestaurantReviewCommentListAdapter(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;

        IMG_SIZE = Math.round(36 * mContext.getResources().getDisplayMetrics().density);
        mUserInformation = MyApplication.getUserInformationInstance();
    }

    @Override
    public RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantReviewCommentListAdapter.RestaurantReviewCommentListViewHolder holder, int position) {

        //등록된 유저의 이미지가 없을 경우
        if (mRestaurantReviewCommentList.get(position).getUserImageURL() == null) {

        } else {
            Glide.with(mContext)
                    .load(mRestaurantReviewCommentList.get(position).getUserImageURL())
                    .apply(new RequestOptions().override(IMG_SIZE, IMG_SIZE).centerCrop().circleCrop())
                    .into(holder.binding.ivUserImage);
        }

        holder.binding.tvUserName.setText(mRestaurantReviewCommentList.get(position).getUserName());
        holder.binding.tvContent.setText(mRestaurantReviewCommentList.get(position).getContent());
        holder.binding.tvCountOfLike.setText(mRestaurantReviewCommentList.get(position).getCountOfLike());
        holder.binding.tvElapsedTime.setText(getElapsedTime(mRestaurantReviewCommentList.get(position).getRegistrationDate()));

        holder.binding.cbLike.setChecked(mRestaurantReviewCommentList.get(position).isLikeCheck());

        if (mRestaurantReviewCommentList.get(position).isLikeCheck()) {
            holder.binding.tvCountOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_ff4f4f));
        } else {
            holder.binding.tvCountOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_666666));
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantReviewCommentList != null ? mRestaurantReviewCommentList.size() : 0;
    }

    public void setRestaurantReviewCommentList(ArrayList<ReviewComment> mRestaurantReviewCommentList) {
        this.mRestaurantReviewCommentList = mRestaurantReviewCommentList;
    }

    public void setRestaurantID(String restaurantID) {
        this.mRestaurantID = restaurantID;
    }

    public void setReviewID(String reviewID) {
        this.mReviewID = reviewID;
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



//    // 좋아요 로띠 애니메이션을 실행 시키는 메소드
//    private void toggleSongLikeAnimButton(LottieAnimationView view , boolean status){
//        if(!status){
//            // 애니메이션을 한번 실행시킨다.
//            // Custom animation speed or duration.
//            // ofFloat(시작 시간, 종료 시간).setDuration(지속시간)
//            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 0.5f).setDuration(1000);
//
//            animator.addUpdateListener(animation -> view.setProgress((Float) animation.getAnimatedValue()));
//            animator.start();
//        }
//        else {
//            // 애니메이션을 한번 실행시킨다.
//            // Custom animation speed or duration.
//            // ofFloat(시작 시간, 종료 시간).setDuration(지속시간)
//            ValueAnimator animator = ValueAnimator.ofFloat(0.5f, 1.0f).setDuration(1000);
//
//            animator.addUpdateListener(animation -> view.setProgress((Float) animation.getAnimatedValue()));
//            animator.start();
//
//
//        }
//
//    }
}