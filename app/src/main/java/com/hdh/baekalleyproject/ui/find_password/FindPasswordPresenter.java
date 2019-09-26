package com.hdh.baekalleyproject.ui.find_password;

import android.app.Activity;
import android.content.Context;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;

public class FindPasswordPresenter extends BaseActivityPresenter implements FindPasswordContract.Presenter {

    private FindPasswordContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public FindPasswordPresenter(FindPasswordContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void enteringEmailText(CharSequence charSequence) {
        if (charSequence.equals("")){
            mView.changeEmailClearButton(false);
        } else {
            mView.changeSendButtonColor(checkEmail(charSequence.toString()));
            mView.changeEmailClearButton(true);
        }
    }

    @Override
    public void clickEmailClear() {
        mView.changeEmailText("");
        mView.changeEmailClearButton(false);
    }

    /**
     * 이메일 형식 체크
     * @param email 사용자가 입력한 이메일
     * @return true  - 이메일 형식
     *         false - 이메일 형식 아님
     */
    private boolean checkEmail(String email) {
        return MyApplication.EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
