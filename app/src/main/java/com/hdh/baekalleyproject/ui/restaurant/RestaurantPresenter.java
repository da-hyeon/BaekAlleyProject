package com.hdh.baekalleyproject.ui.restaurant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.EventImageSliderAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.Event;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.ui.filter.FilterActivity;
import com.hdh.baekalleyproject.ui.search.SearchActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPresenter implements RestaurantContract.Presenter{

    private RestaurantContract.View mView;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private RestaurantList restaurantList;
    private RestaurantList restaurantList_get;


    private EventImageSliderAdapter mEventImageSliderAdapter;

    RestaurantPresenter(RestaurantContract.View mView, Context mContext , FragmentManager mFragmentManager) {
        this.mView = mView;
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
        mEventImageSliderAdapter = new EventImageSliderAdapter(mContext , mFragmentManager);
    }


    @Override
    public void setView(final RecyclerView recyclerView , ViewPager viewPager, TabLayout tabLayout) {

        Call<RestaurantList> eventOnGoingList = MyApplication
                .getRestAdapter()
                .getRestaurantList();

        eventOnGoingList.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.isSuccessful()) {
                    restaurantList = response.body();
                    restaurantList_get = response.body();

                    if (restaurantList != null) {

                        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext , 2);

                        recyclerView.setLayoutManager(mGridLayoutManager);
                        recyclerView.setAdapter(new RestaurantListAdapter( restaurantList.getRestaurantList() , mContext));

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

        ArrayList<Event> eventArrayList = new ArrayList<>();
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));

        mEventImageSliderAdapter.setmEventArrayList(eventArrayList);
        viewPager.setAdapter(mEventImageSliderAdapter);
        tabLayout.setupWithViewPager(viewPager , true);

    }

    /**
     * 필터 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickFilter() {
        mView.moveOptionActivity(new Intent(mContext , FilterActivity.class));
    }

    /**
     * 검색 버튼클릭 이벤트 처리
     */
    @Override
    public void clickSearch() {
        mView.moveOptionActivity(new Intent(mContext , SearchActivity.class));
    }
}
