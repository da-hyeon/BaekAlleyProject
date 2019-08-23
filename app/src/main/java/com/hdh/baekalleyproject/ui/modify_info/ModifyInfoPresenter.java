package com.hdh.baekalleyproject.ui.modify_info;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.adapter.RestaurantWrongItemListAdapter;
import com.hdh.baekalleyproject.data.model.WrongItem;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;

public class ModifyInfoPresenter extends BaseActivityPresenter implements ModifyInfoContract.Presenter{
    private ModifyInfoContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public ModifyInfoPresenter(ModifyInfoContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void setView(RecyclerView recyclerView) {
        ArrayList<WrongItem> reviewArrayList = new ArrayList<>();
        reviewArrayList.add(new WrongItem("문닫은 식당이에요" , false));
        reviewArrayList.add(new WrongItem("식당 주소가 틀려요" , false));
        reviewArrayList.add(new WrongItem("전화번호가 틀려요" , false));
        reviewArrayList.add(new WrongItem("위치표시가 틀려요" , false));
        reviewArrayList.add(new WrongItem("휴무일 정보가 틀려요" , false));
        reviewArrayList.add(new WrongItem("가격 정보가 틀려요" , false));
        reviewArrayList.add(new WrongItem("메뉴 정보가 틀려요" , false));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RestaurantWrongItemListAdapter( reviewArrayList , mContext));
    }
}
