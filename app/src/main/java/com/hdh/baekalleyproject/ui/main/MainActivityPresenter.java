package com.hdh.baekalleyproject.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;
import com.hdh.baekalleyproject.ui.myinfo.MyInfoFragment;
import com.hdh.baekalleyproject.ui.news.NewsFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantFragment;

import java.lang.reflect.Field;

public class MainActivityPresenter extends BaseActivityPresenter implements MainActivityContract.Presenter, BottomNavigationView.OnNavigationItemSelectedListener {

    private MainActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;
    private FragmentManager mFragmentManager;

    private RestaurantFragment mRestaurantFragment;
    private NewsFragment mNewsFragment;
    private MyInfoFragment mMyInfoFragment;

    private int mFrameLayoutID;

    //앱종료시간체크
    private long backPressedTime;    //앱종료 위한 백버튼 누른시간

    MainActivityPresenter(MainActivityContract.View mView, Context mContext, Activity mActivity, FragmentManager mFragmentManager, int mFrameLayoutID) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        this.mFrameLayoutID = mFrameLayoutID;

        mRestaurantFragment = new RestaurantFragment();
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();
    }

    @SuppressLint("RestrictedApi , CutPasteId")
    @Override
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {

                final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                layoutParams.height = Math.round(24 * mContext.getResources().getDisplayMetrics().density);
                layoutParams.width = Math.round(29 * mContext.getResources().getDisplayMetrics().density);
                iconView.setLayoutParams(layoutParams);

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }

        view.setOnNavigationItemSelectedListener(this);
        view.setSelectedItemId(R.id.menuRestaurantSearch);
    }

    @Override
    public void popRestaurantFragment() {
        PopFragment(mRestaurantFragment, mNewsFragment, mMyInfoFragment);
    }

    @Override
    public void popNewsFragment() {
        PopFragment(mNewsFragment, mRestaurantFragment, mMyInfoFragment);
    }

    @Override
    public void popMyInfoFragment() {
        PopFragment(mMyInfoFragment, mRestaurantFragment, mNewsFragment);
    }

    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            mView.showToast("뒤로가기  버튼을 한번 더 누르면 종료합니다.");
        }
        //2번째 백버튼 클릭 (종료)
        else {
            AppFinish();
        }
    }

    private void AppFinish() {
        mView.removeActivity();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRestaurantSearch:
                popRestaurantFragment();
                break;
            case R.id.menuRestaurantNews:
                popNewsFragment();
                break;
            case R.id.menuMyInformation:
                popMyInfoFragment();
                break;
        }
        return true;
    }

    private void PopFragment(Fragment mainFragment, Fragment subFragment_1, Fragment subFragment_2) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mainFragment.isAdded()) {
            fragmentTransaction.show(mainFragment);
        } else {
            fragmentTransaction.add(mFrameLayoutID, mainFragment, mainFragment.getClass().getName());
        }
        if (subFragment_1.isAdded()) {
            fragmentTransaction.hide(subFragment_1);
        }
        if (subFragment_2.isAdded()) {
            fragmentTransaction.hide(subFragment_2);
        }
        fragmentTransaction.commit();
    }
}
