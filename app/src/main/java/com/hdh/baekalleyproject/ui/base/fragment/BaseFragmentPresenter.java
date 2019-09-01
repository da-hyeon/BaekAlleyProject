package com.hdh.baekalleyproject.ui.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.hdh.baekalleyproject.R;

public class BaseFragmentPresenter implements BaseFragmentContract.Presenter {
    private BaseFragmentContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public BaseFragmentPresenter(BaseFragmentContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void setRecyclerViewAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context , R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
