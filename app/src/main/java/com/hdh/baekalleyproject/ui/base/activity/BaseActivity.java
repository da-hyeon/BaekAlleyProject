package com.hdh.baekalleyproject.ui.base.activity;

import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseActivityContract.View {

    @Override
    public void removeActivity() {
        finish();
    }

}