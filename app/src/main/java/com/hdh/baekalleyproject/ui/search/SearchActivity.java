package com.hdh.baekalleyproject.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivitySearchBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private ActivitySearchBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_search);
        mPresenter = new SearchPresenter(this, this, this);

        initData();
    }

    private void initData() {

    }
}
