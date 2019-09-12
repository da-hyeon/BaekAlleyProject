package com.hdh.baekalleyproject.ui.search;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.adapter.RecentSearchTermListAdapter;
import com.hdh.baekalleyproject.adapter.RestaurantListAdapter;
import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter extends BaseActivityPresenter implements SearchContract.Presenter , RecentSearchTermListAdapter.SearchTermClickListener {

    private final int PADDING_SIZE;

    private SearchContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private RestaurantList mRestaurantList;
    private ArrayList<Restaurant> mRestaurantSearchList;
    private RestaurantListAdapter mRestaurantListAdapter;
    private RecentSearchTermListAdapter mRecentSearchTermListAdapter;

    private RecyclerView mSearchListView;

    private ArrayList<RecentSearchTerm> mRecentSearchTerm;

    SearchPresenter(SearchContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        PADDING_SIZE = Math.round(4 * mContext.getResources().getDisplayMetrics().density);
        mRestaurantSearchList = new ArrayList<>();

        mRestaurantListAdapter = new RestaurantListAdapter(mContext , Constants.FILTER_ADAPTER);
        mRecentSearchTermListAdapter = new RecentSearchTermListAdapter(mContext, this);

        mRecentSearchTerm = new ArrayList<>();
    }

    /**
     * 최근검색어 View 세팅
     *
     * @param recyclerView 최근검색어 리스트
     */
    @Override
    public void setRecentSearchView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mRecentSearchTermListAdapter.setRecentSearchTermList(mRecentSearchTerm);
        recyclerView.setAdapter(mRecentSearchTermListAdapter);
    }

    /**
     * restaurantRecyclerView 세팅
     * @param restaurantListView restaurantView
     */
    @Override
    public void setRestaurantList(RecyclerView restaurantListView) {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 2);
        restaurantListView.setLayoutManager(mGridLayoutManager);

        mRestaurantListAdapter.setRestaurantList(mRestaurantSearchList);
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
                    Log.d("fail", "데이터 로딩 실패");
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
     * 검색어 입력 이벤트 처리
     * @param charSequence 입력 값
     */
    @Override
    public void enteringText(CharSequence charSequence) {
        //입력중
        if (charSequence.length() != 0) {
            mView.changeTextPadding(PADDING_SIZE);
            mView.showClearButton();
            mRestaurantSearchList.clear();

            if (mRestaurantList != null && mRestaurantList.getRestaurantList() != null) {
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
            //mRestaurantListAdapter.setRestaurantList(mRestaurantSearchList);
            mRestaurantListAdapter.setCurrentText(charSequence.toString());
            setRecyclerViewAnimation(mSearchListView);

            //검색어에 일치하는 데이터가 없을경우
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
            loadSearchedData();
            mView.changeTextPadding(0);
            mView.hideClearButton();
            mView.showSearchedView();
            mView.hideSearchView();
            mView.hideSearchFailedView();

        }
    }

    /**
     * 클리어 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickClearButton() {
        mView.changeSearchTerm("");
    }


    /**
     * 저장된 검색어 불러오기
     */
    @Override
    public void loadSearchedData(){
        mRecentSearchTerm.clear();
        RealmResults<RecentSearchTerm> realmResults = mRealm.where(RecentSearchTerm.class).findAllAsync();
        mRecentSearchTerm.addAll(realmResults);
        Collections.sort(mRecentSearchTerm);

        if (mRecentSearchTerm.size() == 0) {
            mView.hideDeleteAll();
        } else {
            mView.showDeleteAll();
        }

        mRecentSearchTermListAdapter.setRecentSearchTermList(mRecentSearchTerm);
        mRecentSearchTermListAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchTermClick(String searchTerm) {
        mView.changeSearchTerm(searchTerm);
    }
}
