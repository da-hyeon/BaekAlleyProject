package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdh.baekalleyproject.adapter.AlleyListAdapter;
import com.hdh.baekalleyproject.data.model.Alley;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;

public class FilterPresenter extends BaseActivityPresenter implements FilterContract.Presenter {
    private FilterContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private ArrayList<Alley> mAlleyArrayList;
    private AlleyListAdapter mAlleyListAdapter;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        mAlleyArrayList = new ArrayList<>();
        mAlleyListAdapter = new AlleyListAdapter(mContext);
    }

    @Override
    public void setAlleyView(RecyclerView recyclerView) {

        mAlleyArrayList.add(new Alley("이화여대 삼거리꽃길"));
        mAlleyArrayList.add(new Alley("이태원 해방촌 신흥시장"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        mAlleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAlleyListAdapter.setmAlleyList(mAlleyArrayList);
        recyclerView.setAdapter(mAlleyListAdapter);
    }

    /**
     * 리셋 클릭 이벤트 처리
     */
    @Override
    public void clickReset() {
        mView.changeColorReset();
        for(int i = 0 ; i < mAlleyArrayList.size(); i++) {
            if (!mAlleyArrayList.get(i).getTag().equals("0")) {
                mAlleyArrayList.get(i).setTag("0");
            }
        }
        mAlleyListAdapter.setmAlleyList(mAlleyArrayList);
        mAlleyListAdapter.notifyDataSetChanged();
    }

    /**
     * 음식종류 클릭 이벤트 처리
     */
    @Override
    public void clickFoodType(View view , int index) {
        if (view.getTag().equals("0")){
            mView.changeTintColorOfFoodType(index , true);
            view.setTag("1");
        } else {
            mView.changeTintColorOfFoodType(index , false);
            view.setTag("0");
        }
    }

    /**
     * 가격대 클릭 이벤트 처리
     */
    @Override
    public void clickPriceType(View view , int index) {
        if (view.getTag().equals("0")){
            mView.changeTintColorOfPriceType(index , true);
            view.setTag("1");
        } else {
            mView.changeTintColorOfPriceType(index , false);
            view.setTag("0");
        }
    }
}
