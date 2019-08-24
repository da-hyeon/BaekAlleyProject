package com.hdh.baekalleyproject.ui.photo;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityPhotoBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class PhotoActivity extends BaseActivity implements PhotoContract.View {

    private ActivityPhotoBinding mBinding;
    private PhotoContract.Presenter mPresenter;
    private int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
       // mBinding.pvView.setImageResource(R.drawable.mainbanner_1);
        mPresenter = new PhotoPresenter(this, this, this);

        initData();

        mBinding.ivDismiss.setOnClickListener(v ->
                mPresenter.clickDismiss()
        );

    }

    private void initData() {
        mPresenter.setView(getIntent() , mBinding.vpImages);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setPage(int currentPage , int pageCount) {
        mBinding.tvPage.setText(currentPage + " / " + pageCount);
    }
}
