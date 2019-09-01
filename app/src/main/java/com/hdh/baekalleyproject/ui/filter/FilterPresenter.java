package com.hdh.baekalleyproject.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.adapter.AlleyListAdapter;
import com.hdh.baekalleyproject.data.model.Alley;
import com.hdh.baekalleyproject.data.model.AlleyList;
import com.hdh.baekalleyproject.data.model.FilterSelectedItem;
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

    private SharedPreferences  mPrefs;
    private Gson mGson;

    private FilterSelectedItem mFilterSelectedItem;

    public FilterPresenter(FilterContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        mAlleyListAdapter = new AlleyListAdapter(mContext);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mGson = new Gson();

        loadFilter();
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

                        if (response.code() == 200){
                            setSelectSavedAlley();
                        }
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
     * 저장된 음식종류 선택하기
     */
    @Override
    public void setSelectSavedFoodType(View[] savedFoodTypeViews , TextView[] savedFoodTypeTextViews) {
        if (mFilterSelectedItem != null &&
                mFilterSelectedItem.getSelectedFoodType() != null) {
            for (int i = 0 ; i < savedFoodTypeTextViews.length; i++){
                for(int j = 0; j < mFilterSelectedItem.getSelectedFoodType().size(); j++){
                    if (savedFoodTypeTextViews[i].getText().toString().equals(mFilterSelectedItem.getSelectedFoodType().get(j))){
                        mView.changeTintColorOfFoodType(i, true);
                        savedFoodTypeViews[i].setTag("1");
                        break;
                    }
                }
            }
        }
    }

    /**
     * 저장된 가격대 선택하기
     */
    @Override
    public void setSelectSavedPriceRange(View[] savedPriceRangeViews , TextView[] savedPriceRangeTextViews) {
        if (mFilterSelectedItem != null &&
                mFilterSelectedItem.getSelectedPriceRange() != null){
            for (int i = 0 ; i < savedPriceRangeTextViews.length; i++){
                for(int j = 0; j < mFilterSelectedItem.getSelectedPriceRange().size(); j++){
                    if (savedPriceRangeTextViews[i].getText().toString().equals(mFilterSelectedItem.getSelectedPriceRange().get(j))){
                        mView.changeTintColorOfPriceType(i, true);
                        savedPriceRangeViews[i].setTag("1");
                        break;
                    }
                }
            }
        }
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

        //선택된 필터 저장
       FilterSelectedItem filterSelectedItem = new FilterSelectedItem(
                selectedAlleyCount ,
                selectedFoodTypeCount ,
                selectedPriceRangeCount ,
                selectedCategoriesCount ,
                selectedAlleyList ,
                selectedFoodTypeList ,
                selectedPriceRangeList);

//       //선택필터가 변경되었을때만 저장하기.
//       if (!filterSelectedItem.toString().equals(mFilterSelectedItem.toString())){
//
//       }
        saveFilter(filterSelectedItem);
        clickOptionDismiss();
    }

    private void setSelected(TextView[] textViews, ArrayList<String> arrayList) {
        for (TextView textView : textViews) {
            if (!(textView.getCurrentTextColor() == ContextCompat.getColor(mContext, R.color.colorPrimary)))
                continue;

            arrayList.add(textView.getText().toString());
        }
    }

    /**
     * 선택한 필터 저장하기
     * @param filterSelectedItem filterSelectedItem
     */
    private void saveFilter(FilterSelectedItem filterSelectedItem){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(Constants.FILTER_SAVE_DATA , mGson.toJson(filterSelectedItem));
        editor.apply();
    }

    /**
     * 저장된 필터 불러오기
     */
    private void loadFilter(){
        String json = mPrefs.getString(Constants.FILTER_SAVE_DATA , null);
        if (json != null) {
            mFilterSelectedItem = mGson.fromJson(json , FilterSelectedItem.class);
        }
    }

    /**
     * 저장된 골목 선택하기
     */
    private void setSelectSavedAlley() {
        //선택된 값이 있음
        if (mFilterSelectedItem != null) {

            if (mFilterSelectedItem.getSelectedAlley() != null){
                mAlleyListAdapter.setSelectedItem(mFilterSelectedItem.getSelectedAlley());
            }
        }
    }
}
