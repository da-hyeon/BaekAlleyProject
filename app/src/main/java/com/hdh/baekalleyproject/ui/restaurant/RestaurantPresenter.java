package com.hdh.baekalleyproject.ui.restaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.EventImageSliderAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.Event;
import com.hdh.baekalleyproject.data.model.FilterSelectedItem;
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
    private Activity mActivity;
    private FragmentManager mFragmentManager;

    private RestaurantList restaurantList;
    private RestaurantList restaurantList_get;

    private RestaurantListAdapter mRestaurantListAdapter;
    private EventImageSliderAdapter mEventImageSliderAdapter;

    private FilterSelectedItem mFilterSelectedItem;
    private SharedPreferences mPrefs;
    private Gson mGson;

    private boolean isFirstEntrance;

    RestaurantPresenter(RestaurantContract.View mView, Context mContext , Activity mActivity , FragmentManager mFragmentManager) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        mEventImageSliderAdapter = new EventImageSliderAdapter(mContext , mFragmentManager);
        mRestaurantListAdapter = new RestaurantListAdapter(mContext);
        mFilterSelectedItem = new FilterSelectedItem();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mGson = new Gson();
        isFirstEntrance = false;
    }


    @Override
    public void setView(final RecyclerView recyclerView , ViewPager viewPager, TabLayout tabLayout) {

        loadFilter();

        Call<RestaurantList> getRestaurantList = MyApplication
                .getRestAdapter()
                .setFilter(
                        mFilterSelectedItem.getSelectedAlleyCount(),
                        mFilterSelectedItem.getSelectedFoodTypeCount(),
                        mFilterSelectedItem.getSelectedPriceRangeCount(),
                        mFilterSelectedItem.getSelectedAlley(),
                        mFilterSelectedItem.getSelectedFoodType(),
                        mFilterSelectedItem.getSelectedPriceRange(),
                        mFilterSelectedItem.getSelectedCategoriesCount());

        getRestaurantList.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.isSuccessful()) {
                    restaurantList = response.body();


                    if (restaurantList != null) {
                        //첫 입장시 초기화
                        if(!isFirstEntrance){
                            GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext , 2);
                            recyclerView.setLayoutManager(mGridLayoutManager);

                            mRestaurantListAdapter.setRestaurantList(restaurantList.getRestaurantList());
                            recyclerView.setAdapter(mRestaurantListAdapter);
                            isFirstEntrance = true;
                        } else {
                            mRestaurantListAdapter.setRestaurantList(restaurantList.getRestaurantList());
                            mRestaurantListAdapter.notifyDataSetChanged();
                        }

                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantList> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                Log.d("error", t.getLocalizedMessage());
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
        Intent intent = new Intent(mContext , FilterActivity.class);
        mView.moveOptionActivityForResult(intent , 0);
    }

    /**
     * 검색 버튼클릭 이벤트 처리
     */
    @Override
    public void clickSearch() {
        mView.moveOptionActivity(new Intent(mContext , SearchActivity.class));
    }

    private void loadFilter(){
        String json = mPrefs.getString(Constants.FILTER_SAVE_DATA , null);
        if (json != null) {
            mFilterSelectedItem = mGson.fromJson(json, FilterSelectedItem.class);
            //필터 아이콘 on
        } else {
            //필터 아이콘 off
        }
    }
}
