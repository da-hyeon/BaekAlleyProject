package com.hdh.baekalleyproject.ui.login_email;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;
import com.hdh.baekalleyproject.ui.find_password.FindPasswordActivity;

public class LoginEmailPresenter extends BaseActivityPresenter implements LoginEmailContract.Presenter {

    private LoginEmailContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private boolean emailTypeCheck;
    private boolean passwordLengthCheck;

    public LoginEmailPresenter(LoginEmailContract.View mView, Context mContext, Activity mActivity) {
        super(mView , mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }


    /**
     * 이메일 입력 이벤트 처리
     * @param charSequence 입력중인 텍스트
     */
    @Override
    public void enteringEmailText(CharSequence charSequence) {
        if (charSequence.equals("")){
            mView.changeEmailClearButton(false);
        } else {
            emailTypeCheck = checkEmail(charSequence.toString());
            mView.changeEmailClearButton(true);
        }
        accountTypeCheck();
    }

    /**
     * 패스워드 입력 이벤트 처리
     * @param charSequence 입력중인 텍스트
     */
    @Override
    public void enteringPasswordText(CharSequence charSequence) {
        if (charSequence.equals("")){
            mView.changePasswordClearButton(false);
        } else {
            passwordLengthCheck = charSequence.length() >= 8;
            mView.changePasswordClearButton(true);
        }
        accountTypeCheck();
    }

    /**
     * 이메일 초기화 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickEmailClear() {
        mView.changeEmailText("");
        mView.changeEmailClearButton(false);
    }

    /**
     * 비밀번호 초기화 버튼 클릭 이벤트 처리
     */
    @Override
    public void clickPasswordClear() {
        mView.changePasswordText("");
        mView.changePasswordClearButton(false);
    }

    @Override
    public void clickFindPassword() {
        Intent intent = new Intent(mContext , FindPasswordActivity.class);
        mView.moveActivity(intent);
    }

    @Override
    public void clickRegistration() {

    }

    /**
     * 올바른 계정 형식 확인
     */
    private void accountTypeCheck(){
        if (emailTypeCheck && passwordLengthCheck){
            mView.changeLoginButtonColor(true);
        } else {
            mView.changeLoginButtonColor(false);
        }
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
