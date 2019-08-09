package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.AlleyListAdapter;
import com.hdh.baekalleyproject.data.model.Alley;

import java.util.ArrayList;

public class FilterPresenter implements FilterContract.Presenter {
    private FilterContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void setAlleyView(RecyclerView recyclerView) {
        ArrayList<Alley> alleyArrayList = new ArrayList<>();
        alleyArrayList.add(new Alley("이화여대 삼거리꽃길"));
        alleyArrayList.add(new Alley("이태원 해방촌 신흥시장"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));
        alleyArrayList.add(new Alley("인천 신포국제시장 청년몰"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AlleyListAdapter( alleyArrayList , mContext));
    }

    /**
     * 취소 클릭 이벤트 처리
     * */
    @Override
    public void clickDismiss() {
        mView.removeActivity();
    }

    /**
     * 음식종류 클릭 이벤트 처리
     */
    @Override
    public void clickFoodType(View view) {
      //  Log.d("dd" , view.get().toString());
        if (view.getBackground() == ContextCompat.getDrawable(mContext, R.drawable.round_stroke_bbbbbb)){
            Log.d("맞아", "맞아");
        }
    }

    @Override
    public void clickPriceType(View view) {

    }
}
