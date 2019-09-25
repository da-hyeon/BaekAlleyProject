package com.hdh.baekalleyproject.data.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdh.baekalleyproject.Constants;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private boolean lastPageCheck;

    private int enterType;

    public ItemDecoration(Context mContext, int enterType) {
        this.mContext = mContext;
        this.enterType = enterType;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // only for the last one
        if (enterType == Constants.ITEM_TYPE_RESTAURANT){
           // if (!lastPageCheck) {
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1 || parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 2) {
                    outRect.bottom = Math.round(50 * mContext.getResources().getDisplayMetrics().density);
                }
           // }
        } else if (enterType == Constants.ITEM_TYPE_REVIEW){
            if (!lastPageCheck) {
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = Math.round(80 * mContext.getResources().getDisplayMetrics().density);
                }
            }
        } else {

        }
    }

    public void setLastPageCheck(boolean lastPageCheck) {
        this.lastPageCheck = lastPageCheck;
    }
}
