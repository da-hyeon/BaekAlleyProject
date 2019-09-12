package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.databinding.ItemRecentSearchesBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class RecentSearchTermListAdapter extends RecyclerView.Adapter<RecentSearchTermListAdapter.RestaurantImageListViewHolder> {

    private ArrayList<RecentSearchTerm> mRecentSearchTermList;
    private Context mContext;

    private Realm mRealm;

    private SearchTermClickListener mSearchTermClickListener;

    public interface SearchTermClickListener{
        void searchTermClick(String searchTerm);
    }

    class RestaurantImageListViewHolder extends RecyclerView.ViewHolder {

        private ItemRecentSearchesBinding binding;

        RestaurantImageListViewHolder(ItemRecentSearchesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            //삭제버튼 클릭
            binding.ivDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                removeRecentSearchTerm(mRecentSearchTermList.get(position).getId());
                removeItemView(position);
            });

            //검색어 클릭
            binding.tvSearchTerm.setOnClickListener(v->{
                int position = getAdapterPosition();
                mSearchTermClickListener.searchTermClick(mRecentSearchTermList.get(position).getRecentSearchTerm());
            });
        }
    }

    public RecentSearchTermListAdapter(Context mContext , SearchTermClickListener mSearchTermClickListener) {
        this.mContext = mContext;
        this.mSearchTermClickListener = mSearchTermClickListener;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public RestaurantImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantImageListViewHolder(ItemRecentSearchesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RestaurantImageListViewHolder holder, int position) {
        holder.binding.tvSearchTerm.setText(mRecentSearchTermList.get(position).getRecentSearchTerm());
        holder.binding.tvDateSearched.setText(getElapsedTime(mRecentSearchTermList.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return (mRecentSearchTermList != null ? mRecentSearchTermList.size() : 0);
    }

    public void setRecentSearchTermList(ArrayList<RecentSearchTerm> mRecentSearchTermList) {
        this.mRecentSearchTermList = mRecentSearchTermList;
    }

    /**
     * remove recentSearchTerm from List
     *
     * @param position position
     */
    private void removeItemView(int position) {
        mRecentSearchTermList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mRecentSearchTermList.size());
    }

    /**
     * remove recentSearchTerm from Realm
     *
     * @param id recentSearchTerm id
     */
    private void removeRecentSearchTerm(int id) {

        final RecentSearchTerm recentSearchTerm = mRealm.where(RecentSearchTerm.class).equalTo("id", id).findFirst();

        mRealm.executeTransaction(realm -> {
            if (recentSearchTerm != null) {
                recentSearchTerm.deleteFromRealm();
            }
        });
    }

    /**
     * 날짜 차이 계산
     * @param registrationDate 등록된 날짜
     * @return 날짜 차이
     */
    private String getElapsedTime(Date registrationDate) {
        String elapsedTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentTime = new Date();
        String time = formatter.format(currentTime);

        try {
            currentTime = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
        long diff = registrationDate.getTime() - currentTime.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        diffDays *= -1;
        if (diffDays == 0) {
            elapsedTime = "오늘";
        } else if (diffDays < 7) {
            elapsedTime = diffDays + "일 전";
        } else if (diffDays < 31) {
            elapsedTime = (diffDays / 7) + "주 전";
        } else if (diffDays < 365) {
            elapsedTime = (diffDays % 30) + "달 전";
        } else {
            elapsedTime = (diffDays % 365) + "년 전";
        }

        return elapsedTime;
    }
}