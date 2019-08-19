package com.hdh.baekalleyproject.ui.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.databinding.ActivitySearchBinding;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private ActivitySearchBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_search);
        mPresenter = new SearchPresenter(this, this, this);

        initData();

        //취소 버튼 클릭
        mBinding.tvDismiss.setOnClickListener(v->
            mPresenter.clickOptionDismiss()
        );

        //클리어 버튼 클릭
        mBinding.ivClear.setOnClickListener(v->{
            mPresenter.clickClearButton();
        });

        //텍스트 입력
        mBinding.etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.enteringText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 데이터 생성 및 초기화
     */
    private void initData() {
        mPresenter.setView(mBinding.rvRecentTerm);
    }

    /**
     * 뒤로가기 버튼 클릭
     */
    @Override
    public void onBackPressed() {
        mPresenter.clickOptionDismiss();
    }

    /**
     * 입력 초기화
     */
    @Override
    public void inputInitialization() {
        mBinding.etSearch.setText("");
    }

    /**
     * EditText 패딩 변경
     * @param size 패딩 사이즈
     */
    @Override
    public void changeTextPadding(int size) {
        mBinding.etSearch.setPadding(0,0,0,size);
    }

    /**
     * 클리어 버튼 보여주기
     */
    @Override
    public void showClearButton() {
        mBinding.ivClear.setVisibility(View.VISIBLE);
    }

    /**
     * 클리어 버튼 숨기기
     */
    @Override
    public void hideClearButton() {
        mBinding.ivClear.setVisibility(View.GONE);
    }
}