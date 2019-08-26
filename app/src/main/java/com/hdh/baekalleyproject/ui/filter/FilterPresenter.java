package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.AlleyListAdapter;
import com.hdh.baekalleyproject.data.model.Alley;
import com.hdh.baekalleyproject.data.model.AlleyList;
import com.hdh.baekalleyproject.data.model.RestaurantList;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterPresenter extends BaseActivityPresenter implements FilterContract.Presenter {
    private FilterContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private AlleyList mAlleyArrayList;
    private AlleyListAdapter mAlleyListAdapter;

    private RestaurantList mRestaurantList;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
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
                        mAlleyListAdapter.setAlleyList(mAlleyArrayList.getAlleyArrayList());
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
                Log.d("error", t.getMessage());
                Log.d("error", t.getLocalizedMessage());
            }
        });
    }

    /**
     * 리셋 클릭 이벤트 처리
     */
    @Override
    public void clickReset() {
        mView.changeColorReset();
        for (int i = 0; i < mAlleyArrayList.getAlleyArrayList().size(); i++) {
            if (!mAlleyArrayList.getAlleyArrayList().get(i).getTag().equals("0")) {
                mAlleyArrayList.getAlleyArrayList().get(i).setTag("0");
            }
        }
        mAlleyListAdapter.setAlleyList(mAlleyArrayList.getAlleyArrayList());
        mAlleyListAdapter.notifyDataSetChanged();
    }

    /**
     * 음식종류 클릭 이벤트 처리
     */
    @Override
    public void clickFoodType(View view, int index) {
        if (view.getTag().equals("0")) {
            mView.changeTintColorOfFoodType(index, true);
            view.setTag("1");
        } else {
            mView.changeTintColorOfFoodType(index, false);
            view.setTag("0");
        }
    }

    /**
     * 가격대 클릭 이벤트 처리
     */
    @Override
    public void clickPriceType(View view, int index) {
        if (view.getTag().equals("0")) {
            mView.changeTintColorOfPriceType(index, true);
            view.setTag("1");
        } else {
            mView.changeTintColorOfPriceType(index, false);
            view.setTag("0");
        }
    }

    /**
     * 선택완료 클릭 이벤트 처리
     */
    @Override
    public void clickSelectionComplete(TextView[] foodTypeTextViews, TextView[] priceTypeTextViews) {

        int selectedAlleyCount;                //선택된 골목 개수
        int selectedFoodTypeCount;             //선택된 음식종류 개수
        int selectedPriceRangeCount;           //선택된 가격대 개수
        ArrayList<String> selectedAlleyList = new ArrayList<>();       //선택된 골목 리스트
        ArrayList<String> selectedFoodTypeList = new ArrayList<>();    //선택된 음식종류 리스트
        ArrayList<String> selectedPriceRangeList = new ArrayList<>();  //선택된 가격대 리스트
        int selectedCategoriesCount = 0;    //선택된 카테고리 개수

        //선택된 골목 세팅
        for (Alley alley : mAlleyListAdapter.getAlleyList()) {
            if (alley.getTag().equals("0"))
                continue;

            selectedAlleyList.add(alley.getAlleyName());
        }
        selectedAlleyCount = selectedAlleyList.size();

        //선택된 음식종류 세팅
        setSelected(foodTypeTextViews, selectedFoodTypeList);
        selectedFoodTypeCount = selectedFoodTypeList.size();


        //선택된 가격대 세팅
        setSelected(priceTypeTextViews, selectedPriceRangeList);
        selectedPriceRangeCount = selectedPriceRangeList.size();

        if (selectedAlleyCount > 0) {
            selectedCategoriesCount++;
        }
        if (selectedFoodTypeCount > 0) {
            selectedCategoriesCount++;
        }
        if (selectedPriceRangeCount > 0) {
            selectedCategoriesCount++;
        }

        Log.d("info-선택된 골목 개수", selectedAlleyCount + "");
        Log.d("info-선택된 음식종류 개수", selectedFoodTypeCount + "");
        Log.d("info-선택된 가격대 개수", selectedPriceRangeCount + "");

        Log.d("info-선택된 골목 리스트", Arrays.toString(selectedAlleyList.toArray()));
        Log.d("info-선택된 음식종류 리스트", Arrays.toString(selectedFoodTypeList.toArray()));
        Log.d("info-선택된 가격대 리스트", Arrays.toString(selectedPriceRangeList.toArray()));

        Log.d("info-선택된 카테고리 개수", selectedCategoriesCount + "");

        Call<RestaurantList> getRestaurantDetails = MyApplication
                .getRestAdapter()
                .setFilter(
                        selectedAlleyCount,
                        selectedFoodTypeCount,
                        selectedPriceRangeCount,
                        selectedAlleyList,
                        selectedFoodTypeList,
                        selectedPriceRangeList,
                        selectedCategoriesCount);

        getRestaurantDetails.enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantList> call, @NonNull Response<RestaurantList> response) {
                if (response.isSuccessful()) {
                    mRestaurantList = response.body();

                    if (mRestaurantList != null) {
                        if (response.code() == 200) {
                            Intent intent = new Intent();
                            intent.putExtra(Constants.RESTAURANT_FILTER_LIST, mRestaurantList);
                            mActivity.setResult(0, intent);
                            clickOptionDismiss();
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

    private void setSelected(TextView[] textViews, ArrayList<String> arrayList) {
        for (TextView textView : textViews) {
            if (!(textView.getCurrentTextColor() == ContextCompat.getColor(mContext, R.color.colorPrimary)))
                continue;

            arrayList.add(textView.getText().toString());
        }
    }
}
