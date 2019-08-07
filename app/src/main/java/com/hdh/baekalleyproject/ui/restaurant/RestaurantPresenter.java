package com.hdh.baekalleyproject.ui.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.RestaurantList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPresenter implements RestaurantContract.Presenter{

    private RestaurantContract.View mView;
    private Context mContext;

    RestaurantPresenter(RestaurantContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }


    @Override
    public void setView(final RecyclerView recyclerView) {

        Call<RestaurantList> eventOnGoingList = MyApplication
                .getRestAdapter()
                .getRestaurantList();

        eventOnGoingList.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.isSuccessful()) {
                    RestaurantList restaurantList = response.body();
                    if (restaurantList != null) {

                        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext , 2);

                        recyclerView.setLayoutManager(mGridLayoutManager);
                        recyclerView.setAdapter(new RestaurantListAdapter( restaurantList.getRestaurantList() , mContext));

//                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mGridLayoutManager.getOrientation());
//                        recyclerView.addItemDecoration(dividerItemDecoration);


                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantList> call, @NonNull Throwable t) {
                Log.d("error" , t.getMessage());
                Log.d("error" , t.getLocalizedMessage());
            }
        });

    }

    /**
     * 필터 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickFilter() {
        //mView.moveActivity(new Intent(mContext , FilterActivity.class));
    }

    /**
     * 검색 버튼클릭 이벤트 처리
     */
    @Override
    public void clickSearch() {

    }
}
