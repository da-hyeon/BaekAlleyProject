package com.hdh.baekalleyproject.ui.main;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivityMainBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private ActivityMainBinding mBinding;
    private MainActivityContract.Presenter mPresenter;

    private int mWidth, mHeight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setMainActivity(this);
       // mPresenter = new MainActivityPresenter(this, this, this, getSupportFragmentManager(), mBinding.flView.getId());
        mPresenter = new MainActivityPresenter(this, this, this, getSupportFragmentManager(), mBinding.vpView);

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
        mBinding.vSelectLine.setLayoutParams(new ConstraintLayout.LayoutParams(mWidth / 3, Math.round(2 * getResources().getDisplayMetrics().density)));
        mBinding.vSelectLine.setY(mBinding.vLine.getY());
        mBinding.vSelectLine.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void moveMenuBar(int dp) {
        mBinding.vSelectLine.animate().translationX(dp);
    }

    @Override
    public int getWindowWidth() {
        return mWidth;
    }

    @Override
    public void showMenu() {
        mBinding.llBottomView.animate().translationY(Math.round(50 * getResources().getDisplayMetrics().density));
    }

    @Override
    public void hideMenu() {
        mBinding.llBottomView.animate().translationY(0);

    }
}
