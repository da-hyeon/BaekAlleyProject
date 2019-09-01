package com.hdh.baekalleyproject.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.RecentSearchTermListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter extends BaseActivityPresenter implements SearchContract.Presenter {

    private final int PADDING_SIZE;

    private SearchContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private RestaurantList mRestaurantList;
    private ArrayList<Restaurant> mRestaurantSearchList;
    private RestaurantListAdapter mRestaurantListAdapter;

    private SharedPreferences mPrefs;
    private Gson mGson;

    private RecyclerView mSearchListView;

    SearchPresenter(SearchContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        PADDING_SIZE = Math.round(4 * mContext.getResources().getDisplayMetrics().density);
        mRestaurantSearchList = new ArrayList<>();

        mRestaurantListAdapter = new RestaurantListAdapter(mContext , Constants.FILTER_ADAPTER);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mGson = new Gson();
    }

    /**
     * 최근검색어 View 세팅
     *
     * @param recyclerView 최근검색어 리스트
     */
    @Override
    public void setRecentSearchView(RecyclerView recyclerView) {
        ArrayList<RecentSearchTerm> recentSearchTermArrayList = new ArrayList<>();
        recentSearchTermArrayList.add(new RecentSearchTerm("이화여대 삼거리 꽃길", "1일 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("포방터 시장", "3일 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("붹붹버거", "1주 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("투움바 함박", "1달 전"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecentSearchTermListAdapter(recentSearchTermArrayList, mContext));


    }

    @Override
    public void setRestaurantList(RecyclerView restaurantListView) {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 2);
        restaurantListView.setLayoutManager(mGridLayoutManager);

        restaurantListView.setAdapter(mRestaurantListAdapter);
        mSearchListView = restaurantListView;

    }

    /**
     * 서버에 Restaurant List 요청
     */
    @Override
    public void loadRestaurantList() {
        Call<RestaurantList> getRestaurantList = MyApplication
                .getRestAdapter()
                .getRestaurantList();

        getRestaurantList.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.body() != null) {
                    mRestaurantList = response.body();
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

    @Override
    public void enteringText(CharSequence charSequence) {
        //입력중
        if (charSequence.length() != 0) {
            mView.changeTextPadding(PADDING_SIZE);
            mView.showClearButton();
            mRestaurantSearchList.clear();

            if (mRestaurantList != null && mRestaurantList.getRestaurantList() != null) {
                Log.d("restaurantInfo" , mRestaurantList.getRestaurantList().get(0).toString());
                for (Restaurant restaurant : mRestaurantList.getRestaurantList()) {
                    //식당 이름으로 검색
                    if (restaurant.getRestaurantAlley().contains(charSequence.toString())) {
                        mRestaurantSearchList.add(restaurant);
                    }
                    //골목으로 검색
                    else if (restaurant.getRestaurantName().contains(charSequence.toString())) {
                        mRestaurantSearchList.add(restaurant);
                    }
                    //메뉴로 검색
                    else if (restaurant.getRestaurantRepFood().contains(charSequence.toString())) {
                        mRestaurantSearchList.add(restaurant);
                    }
                }

            }
            mRestaurantListAdapter.setRestaurantList(mRestaurantSearchList);
            setRecyclerViewAnimation(mSearchListView);


            if (mRestaurantSearchList.size() == 0) {
                mView.hideSearchedView();
                mView.hideSearchView();
                mView.showSearchFailedView();
                mView.changeSearchedFailedText("\"" + charSequence.toString() + "\"에 해당되는\n식당 및 골목을 찾지 못했습니다.");
            } else {
                mView.showSearchView();
                mView.hideSearchedView();
                mView.hideSearchFailedView();
                mView.changeSearchText(charSequence.toString());
            }
        }
        //입력값 없음
        else {
            mView.changeTextPadding(0);
            mView.hideClearButton();
            mView.showSearchedView();
            mView.hideSearchView();
            mView.hideSearchFailedView();
        }
    }

    @Override
    public void clickClearButton() {
        mView.inputInitialization();
    }
}
