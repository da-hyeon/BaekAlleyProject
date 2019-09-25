package com.hdh.baekalleyproject.ui.myinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.ui.base.fragment.BaseFragment;

public class MyInfoFragment extends BaseFragment {

    public MyInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume" , "myInfo 진입");
    }
}
