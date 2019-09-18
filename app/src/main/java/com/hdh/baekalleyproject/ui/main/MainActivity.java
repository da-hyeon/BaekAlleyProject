package com.hdh.baekalleyproject.ui.main;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Display;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityMainBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private ActivityMainBinding mBinding;
    private MainActivityContract.Presenter mPresenter;

    private int mWidth , mHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_main);
        mBinding.setMainActivity(this);
        mPresenter = new MainActivityPresenter(this, this, this , getSupportFragmentManager(), mBinding.flView.getId());

        initData();
    }

    /**
     * 데이터 초기화 및 생성
     */
    private void initData() {
        isPermissionCheck();

        mPresenter.disableShiftMode(mBinding.bnMenu);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mBinding.vSelectLine.setLayoutParams(new ConstraintLayout.LayoutParams(mWidth / 3 , mBinding.vSelectLine.getHeight()));
        mBinding.vSelectLine.setY(mBinding.vLine.getY());

    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }
}
