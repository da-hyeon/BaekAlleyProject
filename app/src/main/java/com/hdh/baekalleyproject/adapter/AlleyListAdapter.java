package com.hdh.baekalleyproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
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

            binding.vAlley.setOnClickListener(v1->{
                int position = getAdapterPosition();
                if(mAlleyList.get(position).getTag().equals("0")){
                    mAlleyList.get(position).setTag("1");
                } else {
                    mAlleyList.get(position).setTag("0");
                }
                notifyItemChanged(position);
            });
        }


    }

    public AlleyListAdapter( Context mContext) {
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

        if (mAlleyList.get(position).getTag().equals("0")){
            holder.binding.vAlley.setBackground(ContextCompat.getDrawable(mContext , R.drawable.round_stroke_bbbbbb));
            holder.binding.tvArea.setTextColor(mContext.getResources().getColor(R.color.foodTextDefaultColor));
            holder.binding.tvAlley.setTextColor(mContext.getResources().getColor(R.color.foodTextDefaultColor));
            holder.binding.vDot.setVisibility(View.INVISIBLE);

        }
        else {
            holder.binding.vAlley.setBackground(ContextCompat.getDrawable(mContext , R.drawable.round_stroke_ff4f4f));
            holder.binding.tvArea.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.binding.tvAlley.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.binding.vDot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mAlleyList != null ? mAlleyList.size() : 0;
    }

    public void setAlleyList(ArrayList<Alley> mAlleyList) {
        this.mAlleyList = mAlleyList;
    }

    public ArrayList<Alley> getAlleyList() {
        return mAlleyList;
    }

    public void setSelectedItem(ArrayList<String> selectedItemList){
        for (int i = 0 ; i < mAlleyList.size(); i++){
            for(int j = 0 ; j < selectedItemList.size(); j++){
                if (mAlleyList.get(i).getAlleyName().equals(selectedItemList.get(j))){
                    mAlleyList.get(i).setTag("1");
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }
}