package com.hdh.baekalleyproject.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
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
import com.hdh.baekalleyproject.adapter.ContentViewPagerAdapter;
import com.hdh.baekalleyproject.data.util.NonSwipeViewPager;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;
import com.hdh.baekalleyproject.ui.myinfo.MyInfoFragment;
import com.hdh.baekalleyproject.ui.news.NewsFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantFragment;
import com.hdh.baekalleyproject.ui.restaurant.RestaurantPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends BaseActivityPresenter implements MainActivityContract.Presenter, BottomNavigationView.OnNavigationItemSelectedListener , RestaurantPresenter.ScrollEvent {

    private MainActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;
    private FragmentManager mFragmentManager;

    private RestaurantFragment mRestaurantFragment;
    private NewsFragment mNewsFragment;
    private MyInfoFragment mMyInfoFragment;

    private int mFrameLayoutID;
    private NonSwipeViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    //앱종료시간체크
    private long backPressedTime;    //앱종료 위한 백버튼 누른시간

    private long mScrollingLastClickTime;
    private long mScrollStopLastClickTime;

    /**
     * Fragment로 설정 시 생성자
     * @param mView MainActivityContract.View
     * @param mContext Context
     * @param mActivity Activity
     * @param mFragmentManager FragmentManager
     * @param mFrameLayoutID FrameLayout의 ID
     */
    MainActivityPresenter(MainActivityContract.View mView, Context mContext, Activity mActivity, FragmentManager mFragmentManager, int mFrameLayoutID) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        this.mFrameLayoutID = mFrameLayoutID;

        mRestaurantFragment = new RestaurantFragment(this);
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();
    }

    /**
     * ViewPager로 설정 시 생성자
     * @param mView MainActivityContract.View
     * @param mContext Context
     * @param mActivity Activity
     * @param mFragmentManager FragmentManager
     * @param mViewPager NonSwipeViewPager
     */
    MainActivityPresenter(MainActivityContract.View mView, Context mContext, Activity mActivity, FragmentManager mFragmentManager , NonSwipeViewPager mViewPager) {
        super(mView, mContext, mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        this.mViewPager = mViewPager;

        mRestaurantFragment = new RestaurantFragment(this);
        mNewsFragment = new NewsFragment();
        mMyInfoFragment = new MyInfoFragment();

        mFragmentList.add(mRestaurantFragment);
        mFragmentList.add(mNewsFragment);
        mFragmentList.add(mMyInfoFragment);

        mViewPager.setAdapter(new ContentViewPagerAdapter(mFragmentManager , mFragmentList));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
    }

    /**
     * BottomNavigationView 설정
     * @param view BottomNavigationView
     */
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

    /**
     * RestaurantFragment 보여주기
     */
    @Override
    public void popRestaurantFragment() {
        //PopFragment(mRestaurantFragment, mNewsFragment, mMyInfoFragment);
    }

    /**
     * NewsFragment 보여주기
     */
    @Override
    public void popNewsFragment() {
        //PopFragment(mNewsFragment, mRestaurantFragment, mMyInfoFragment);
    }

    /**
     * MyInfoFragment 보여주기
     */
    @Override
    public void popMyInfoFragment() {
       // PopFragment(mMyInfoFragment, mRestaurantFragment, mNewsFragment);
    }

    /**
     * 뒤로가기 클릭 이벤트 처리
     */
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

    /**
     * 앱 종료 처리
     */
    private void AppFinish() {
        mView.removeActivity();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 메뉴 클릭 이벤트 처리
     * @param item 메뉴 번호
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRestaurantSearch:
                //popRestaurantFragment();
                mViewPager.setCurrentItem(0);
                mView.moveMenuBar(0);
                break;
            case R.id.menuRestaurantNews:
                //popNewsFragment();
                mViewPager.setCurrentItem(1);
                mView.moveMenuBar(mView.getWindowWidth() / 3);
                break;
            case R.id.menuMyInformation:
                //popMyInfoFragment();
                mViewPager.setCurrentItem(2);
                mView.moveMenuBar(mView.getWindowWidth() / 3 * 2);
                break;
        }
        return true;
    }

    /**
     * Fragment Pop 처리
     * @param mainFragment Fragment
     * @param subFragment_1 Fragment
     * @param subFragment_2 Fragment
     */
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

    /**
     * RestaurantFragment 스크롤링 처리
     */
    @Override
    public void scrolling() {
        if (SystemClock.elapsedRealtime() - mScrollingLastClickTime < 500){
            return;
        }
        mScrollingLastClickTime = SystemClock.elapsedRealtime();
        mView.showMenu();
    }

    /**
     * RestaurantFragment 스크롤 스탑 처리
     */
    @Override
    public void scrollStop() {
        if (SystemClock.elapsedRealtime() - mScrollStopLastClickTime < 500){
            return;
        }
        mScrollStopLastClickTime = SystemClock.elapsedRealtime();
        mView.hideMenu();
    }
}