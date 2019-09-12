package com.hdh.baekalleyproject.ui.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.google.gson.Gson;
import com.hdh.baekalleyproject.R;

import io.realm.Realm;

public class BaseActivityPresenter implements BaseActivityContract.Presenter {

    private BaseActivityContract.View mView;
    private Context mContext;
    private Activity mActivity;

    protected SharedPreferences mPrefs;
    protected Gson mGson;

    protected Realm mRealm;

    public BaseActivityPresenter(BaseActivityContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mRealm = Realm.getDefaultInstance();

        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mGson = new Gson();
    }

    @Override
    public void clickDismiss() {
        mView.removeActivity();
    }

    @Override
    public void clickOptionDismiss() {
        mView.removeActivity();
        mActivity.overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void setRecyclerViewAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context , R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
