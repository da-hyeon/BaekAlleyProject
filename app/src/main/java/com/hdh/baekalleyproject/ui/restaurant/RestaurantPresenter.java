package com.hdh.baekalleyproject.ui.restaurant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.EventImageSliderAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.Event;
import com.hdh.baekalleyproject.data.model.FilterSelectedItem;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.data.util.CustomNestedScrollView;
import com.hdh.baekalleyproject.data.util.ItemDecoration;
import com.hdh.baekalleyproject.ui.base.fragment.BaseFragmentPresenter;
import com.hdh.baekalleyproject.ui.filter.FilterActivity;
import com.hdh.baekalleyproject.ui.search.SearchActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPresenter extends BaseFragmentPresenter implements RestaurantContract.Presenter {

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

    private RecyclerView mRestaurantListView;

    private int mCurrentPage;
    private ItemDecoration mItemDecoration;

    public interface ScrollEvent {
        void scrolling();

        void scrollStop();
    }

    private ScrollEvent mScrollEvent;

    RestaurantPresenter(RestaurantContract.View mView, Context mContext, Activity mActivity, FragmentManager mFragmentManager, RecyclerView restaurantRecyclerView , ScrollEvent mScrollEvent) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        mRestaurantListView = restaurantRecyclerView;

        mEventImageSliderAdapter = new EventImageSliderAdapter(mContext, mFragmentManager);
        mRestaurantListAdapter = new RestaurantListAdapter(mContext, Constants.MAIN_ADAPTER);

        mFilterSelectedItem = new FilterSelectedItem();

        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mGson = new Gson();

        mCurrentPage = 1;
        mItemDecoration = new ItemDecoration(mContext, Constants.ITEM_TYPE_RESTAURANT);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 2);
        restaurantRecyclerView.setLayoutManager(mGridLayoutManager);
        restaurantRecyclerView.setAdapter(mRestaurantListAdapter);
        restaurantRecyclerView.addItemDecoration(mItemDecoration);

        this.mScrollEvent = mScrollEvent;
    }

    /**
     * 식당 뷰 갱신
     */
    @Override
    public void setRestaurantView() {
        mView.showLoading();

        loadFilter();
        mItemDecoration.setLastPageCheck(false);
        mCurrentPage = 1;
        Call<RestaurantList> getRestaurantList = MyApplication
                .getRestAdapter()
                .setFilter(
                        mFilterSelectedItem.getSelectedAlleyCount(),
                        mFilterSelectedItem.getSelectedFoodTypeCount(),
                        mFilterSelectedItem.getSelectedPriceRangeCount(),
                        mFilterSelectedItem.getSelectedAlley(),
                        mFilterSelectedItem.getSelectedFoodType(),
                        mFilterSelectedItem.getSelectedPriceRange(),
                        mFilterSelectedItem.getSelectedCategoriesCount(),
                        1);

        getRestaurantList.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.code() == 200) {
                    restaurantList = response.body();

                    if (restaurantList != null) {

                        mRestaurantListAdapter.setRestaurantList(restaurantList.getRestaurantList());
                        setRecyclerViewAnimation(mRestaurantListView);
                        mView.hideLoading();

                        if (mCurrentPage >= restaurantList.getMaxPage()) {
                            mItemDecoration.setLastPageCheck(true);
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


    }

    /**
     * 광고 뷰 세팅
     *
     * @param viewPager viewPager
     * @param tabLayout tabLayout
     */
    @Override
    public void setAdvertisementView(ViewPager viewPager, TabLayout tabLayout) {

        ArrayList<Event> eventArrayList = new ArrayList<>();
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));
        eventArrayList.add(new Event("http://leehwangco.cafe24.com/resources/shopImg/mainbanner_1.png"));

        mEventImageSliderAdapter.setmEventArrayList(eventArrayList);

        viewPager.setAdapter(mEventImageSliderAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    /**
     * 데이터 동적 로드
     * @param nestedScrollView scrollView
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void dataLoadMore(CustomNestedScrollView nestedScrollView) {

        nestedScrollView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                nestedScrollView.startScrollerTask();
            }
            return false;
        });

        nestedScrollView.setOnScrollStoppedListener(new CustomNestedScrollView.OnScrollStoppedListener() {
            @Override
            public void onScrollStopped() {
                Log.e("scroll" , "stop");
                mScrollEvent.scrollStop();
            }

            @Override
            public void onScrolling() {
                Log.e("scroll" , "ing");
                mScrollEvent.scrolling();
            }
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
           // mScrollEvent.scrolling();

            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling
                    Log.e("mCurrentPage..", mCurrentPage + "");
                    Log.e("mMaxPage..", restaurantList.getMaxPage() + "");
                    mCurrentPage++;
                    if (mCurrentPage <= restaurantList.getMaxPage()) {
                        mView.showListLoading();

                        Call<RestaurantList> getRestaurantList = MyApplication
                                .getRestAdapter()
                                .setFilter(
                                        mFilterSelectedItem.getSelectedAlleyCount(),
                                        mFilterSelectedItem.getSelectedFoodTypeCount(),
                                        mFilterSelectedItem.getSelectedPriceRangeCount(),
                                        mFilterSelectedItem.getSelectedAlley(),
                                        mFilterSelectedItem.getSelectedFoodType(),
                                        mFilterSelectedItem.getSelectedPriceRange(),
                                        mFilterSelectedItem.getSelectedCategoriesCount(),
                                        mCurrentPage);

                        getRestaurantList.enqueue(new Callback<RestaurantList>() {
                            @Override
                            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                                if (response.body() != null) {
                                    restaurantList.getRestaurantList().addAll(response.body().getRestaurantList());
                                    mRestaurantListAdapter.notifyDataSetChanged();

                                    if (response.code() == 200) {
                                        mView.hideListLoading();
                                    }

                                } else {
                                    //mView.showFailDialog("실패" , "데이터 로딩 실패");
                                    Log.d("실패", "데이터 로딩 실패");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<RestaurantList> call, @NonNull Throwable t) {
                                Log.d("error", t.getMessage());
                                Log.d("error", t.getLocalizedMessage());
                            }
                        });
                    }
                    if (mCurrentPage >= restaurantList.getMaxPage()) {
                        mItemDecoration.setLastPageCheck(true);
                    }
                }
            }
        });
    }

    /**
     * 필터 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickFilter() {
        Intent intent = new Intent(mContext, FilterActivity.class);
        mView.moveOptionActivityForResult(intent, 0);
    }

    /**
     * 검색 버튼클릭 이벤트 처리
     */
    @Override
    public void clickSearch() {
        mView.moveOptionActivity(new Intent(mContext, SearchActivity.class));
    }

    private void loadFilter() {
        String json = mPrefs.getString(Constants.FILTER_SAVE_DATA, null);
        if (json != null) {
            mFilterSelectedItem = mGson.fromJson(json, FilterSelectedItem.class);
            //필터 아이콘 on
            Log.d("filterIcon", "on");
        } else {
            //필터 아이콘 off
            Log.d("filterIcon", "off");
        }
    }

}
