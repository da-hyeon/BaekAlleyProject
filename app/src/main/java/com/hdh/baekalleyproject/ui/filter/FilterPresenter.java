package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.AlleyListAdapter;
import com.hdh.baekalleyproject.data.model.AlleyList;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterPresenter extends BaseActivityPresenter implements FilterContract.Presenter {
    private FilterContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private AlleyList mAlleyArrayList;
    private AlleyListAdapter mAlleyListAdapter;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        mAlleyListAdapter = new AlleyListAdapter(mContext);
    }

    @Override
    public void setAlleyView(RecyclerView recyclerView) {


        Call<AlleyList> getAlleyList = MyApplication
                .getRestAdapter()
                .getAlleyList();

        getAlleyList.enqueue(new Callback<AlleyList>() {
            @Override
            public void onResponse(@NonNull Call<AlleyList> call, @NonNull Response<AlleyList> response) {
                if (response.isSuccessful()) {
                    mAlleyArrayList = response.body();

                    if (mAlleyArrayList != null) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        mAlleyListAdapter.setmAlleyList(mAlleyArrayList.getAlleyArrayList());
                        recyclerView.setAdapter(mAlleyListAdapter);

                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                        mView.removeActivity();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AlleyList> call, @NonNull Throwable t) {
                Log.d("error" , t.getMessage());
                Log.d("error" , t.getLocalizedMessage());
            }
        });
    }

    /**
     * 리셋 클릭 이벤트 처리
     */
    @Override
    public void clickReset() {
        mView.changeColorReset();
        for(int i = 0 ; i < mAlleyArrayList.getAlleyArrayList().size(); i++) {
            if (!mAlleyArrayList.getAlleyArrayList().get(i).getTag().equals("0")) {
                mAlleyArrayList.getAlleyArrayList().get(i).setTag("0");
            }
        }
        mAlleyListAdapter.setmAlleyList(mAlleyArrayList.getAlleyArrayList());
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
