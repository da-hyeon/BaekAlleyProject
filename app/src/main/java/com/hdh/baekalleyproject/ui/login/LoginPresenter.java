package com.hdh.baekalleyproject.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.hdh.baekalleyproject.data.util.SessionCallBack;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Context mContext;
    private Activity mActivity;

    public LoginPresenter(LoginContract.View mView, Context mContext, Activity mActivity) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void getHashKey() {
        try {                                                        // 패키지이름을 입력해줍니다.
            PackageInfo info = mContext.getPackageManager().getPackageInfo("com.hdh.baekalleyproject", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey","key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickKakaoLogin() {
        Session.getCurrentSession().addCallback(new SessionCallBack());
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, mActivity);
    }

    @Override
    public void clickEmailLogin() {

    }

    @Override
    public void clickNoneLogin() {

    }


}
