package com.hdh.baekalleyproject.ui.search;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hdh.baekalleyproject.adapter.RecentSearchTermListAdapter;
import com.hdh.baekalleyproject.data.model.RecentSearchTerm;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

import java.util.ArrayList;

public class SearchPresenter extends BaseActivityPresenter implements SearchContract.Presenter{

    private final int PADDING_SIZE;

    private SearchContract.View mView;
    private Context mContext;
    private Activity mActivity;


    SearchPresenter(SearchContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        PADDING_SIZE = Math.round(4 * mContext.getResources().getDisplayMetrics().density);
    }

    /**
     * View 세팅
     * @param recyclerView 최근검색어 리스트
     */
    @Override
    public void setView(RecyclerView recyclerView) {
        ArrayList<RecentSearchTerm> recentSearchTermArrayList = new ArrayList<>();
        recentSearchTermArrayList.add(new RecentSearchTerm("이화여대 삼거리 꽃길" , "1일 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("포방터 시장" , "3일 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("붹붹버거" , "1주 전"));
        recentSearchTermArrayList.add(new RecentSearchTerm("투움바 함박" , "1달 전"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecentSearchTermListAdapter( recentSearchTermArrayList , mContext));
    }

    @Override
    public void enteringText(CharSequence charSequence) {
        if (charSequence.length() != 0){
            mView.changeTextPadding(PADDING_SIZE);
            mView.showClearButton();
        } else {
            mView.changeTextPadding(0);
            mView.hideClearButton();
        }
    }

    @Override
    public void clickClearButton() {
        mView.inputInitialization();
    }
}
