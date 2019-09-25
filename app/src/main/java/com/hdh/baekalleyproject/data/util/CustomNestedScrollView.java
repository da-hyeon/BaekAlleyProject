package com.hdh.baekalleyproject.data.util;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

public class CustomNestedScrollView extends NestedScrollView {


    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;
    private static final String TAG = "MyScrollView";

    public interface OnScrollStoppedListener{
        void onScrollStopped();
        void onScrolling();
    }

    private OnScrollStoppedListener onScrollStoppedListener;

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scrollerTask = () -> {

            int newPosition = getScrollY();
            if(initialPosition - newPosition == 0){//has stopped

                if(onScrollStoppedListener!=null){
                    onScrollStoppedListener.onScrollStopped();
                }
            }else{
                if(onScrollStoppedListener!=null){
                    onScrollStoppedListener.onScrolling();
                }
                initialPosition = getScrollY();
                CustomNestedScrollView.this.postDelayed(scrollerTask, newCheck);
            }
        };
    }

    public void setOnScrollStoppedListener(CustomNestedScrollView.OnScrollStoppedListener listener){
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask(){

        initialPosition = getScrollY();
        CustomNestedScrollView.this.postDelayed(scrollerTask, newCheck);
    }
}
