package com.hdh.baekalleyproject.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.MyApplication;
import com.hdh.baekalleyproject.data.model.UserInformation;
import com.hdh.baekalleyproject.ui.base.activity.BaseActivityPresenter;
import com.hdh.baekalleyproject.ui.main.MainActivity;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BaseActivityPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Context mContext;
    private Activity mActivity;

    private String TAG = "KAKAO";
    private SessionCallback mCallback;

    private UserInformation mUserInformation;

    LoginPresenter(LoginContract.View mView, Context mContext, Activity mActivity) {
        super(mView, mContext , mActivity);
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = mActivity;

        mCallback = new SessionCallback();
    }

    @Override
    public void initView() {
//        Session.getCurrentSession().addCallback(mCallback);
//        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, mActivity);

        /** 토큰 만료시 갱신을 시켜준다**/
        if (Session.getCurrentSession().isOpenable()) {
            Session.getCurrentSession().checkAndImplicitOpen();
        }

        Log.e(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.e(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.e(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());

        if (Session.getCurrentSession().getTokenInfo().getAccessToken() != null) {
            mUserInformation = MyApplication.getUserInformationInstance();
            //자동 로그인
            Log.e(TAG, "자동 로그인 실행");
            Log.e(TAG, "자동 로그인 이메일" + mUserInformation.getEmail());
            moveMain();

            Call<Void> requestRegisterLoginLog = MyApplication
                    .getRestAdapter()
                    .requestRegistrationLoginLog(
                            mUserInformation.getEmail());

            requestRegisterLoginLog.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.code() == 200) {
                        Log.e("성공" , "로그인 로그 저장");
                    } else {
                        //mView.showFailDialog("실패" , "데이터 로딩 실패");
                        Log.d("실패", "데이터 로딩 실패");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                    Log.d("error", t.getLocalizedMessage());
                }
            });

        } else {
            Log.e(TAG, "자동 로그인 실패");
        }
    }

    @Override
    public void getHashKey() {
        try {                                                        // 패키지이름을 입력해줍니다.
            PackageInfo info = mContext.getPackageManager().getPackageInfo("com.hdh.baekalleyproject", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickKakaoLogin() {
        Session.getCurrentSession().addCallback(mCallback);
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, mActivity);

        /** 토큰 만료시 갱신을 시켜준다**/
        if (Session.getCurrentSession().isOpenable()) {
            Session.getCurrentSession().checkAndImplicitOpen();
        }

        Log.e(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.e(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.e(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());

    }

    @Override
    public void clickEmailLogin() {
       // onClickLogout();
        //이메일로 로그인 액티비티로 이동
    }

    @Override
    public void clickNoneLogin() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mActivity.startActivity(intent);
        mView.removeActivity();
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e(TAG, "카카오 로그인 성공 ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(TAG, "exception : " + exception);
            }
        }
    }

    /**
     * 사용자에 대한 정보를 가져온다
     **/
    private void requestMe() {

        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e(TAG, "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e(TAG, "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.e(TAG, "ID : " + result.getId());
                Log.e(TAG, "EMAIL : " + result.getKakaoAccount().getEmail());
                Log.e(TAG, "NICK_NAME : " + result.getNickname());
                Log.e(TAG, "PROFILE_IMAGE : " + result.getProfileImagePath());
                Log.e(TAG, "Thumbnail_IMAGE : " + result.getThumbnailImagePath());
                Log.e(TAG, "PHONE_NUMBER : " + result.getKakaoAccount().getPhoneNumber());
                Log.e(TAG, "GENDER : " + result.getKakaoAccount().getGender());

                Log.e(TAG, "requestMe onSuccess message : " + result.getKakaoAccount().getEmail() + " " + result.getId() + " " + result.getNickname());

                String androidID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

                Call<UserInformation> requestRegisterKAKAO = MyApplication
                        .getRestAdapter()
                        .requestRegistrationKAKAO(
                                result.getKakaoAccount().getEmail(),
                                result.getNickname(),
                                result.getProfileImagePath(),
                                getAppVersionName(),
                                androidID,
                                "Y");

                requestRegisterKAKAO.enqueue(new Callback<UserInformation>() {
                    @Override
                    public void onResponse(@NonNull Call<UserInformation> call, @NonNull Response<UserInformation> response) {
                        if (response.code() == 200) {
                            mUserInformation = response.body();

                            if (mUserInformation != null) {
                                saveUser(mUserInformation);
                                moveMain();
                            } else {
                                //mView.showFailDialog("실패" , "데이터 로딩 실패");
                                Log.d("실패", "데이터 로딩 실패");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserInformation> call, @NonNull Throwable t) {
                        Log.d("error", t.getMessage());
                        Log.d("error", t.getLocalizedMessage());
                    }
                });
            }


        });
    }

    /**
     * 로그아웃시
     **/
    private void onClickLogout() {

        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "카카오 로그아웃 onSessionClosed");
            }

            @Override
            public void onNotSignedUp() {
                Log.e(TAG, "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e(TAG, "카카오 로그아웃 onSuccess");
            }
        });
    }

    //앱버전 명
    private String getAppVersionName() {
        PackageInfo packageInfo = null;         //패키지에 대한 전반적인 정보

        //PackageInfo 초기화
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        return packageInfo.versionName;
    }

    private void moveMain() {
        Intent intent = new Intent(mContext, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * 유저 정보 저장하기
     *
     * @param userInformation recentSearchTermList
     */
    private void saveUser(UserInformation userInformation) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(Constants.USER_SAVE_DATA, mGson.toJson(userInformation));
        editor.apply();
    }

    /**
     * 저장된 유저 정보 불러오기
     */
//    private void loadUser() {
//        String json = mPrefs.getString(Constants.USER_SAVE_DATA, null);
//        if (json != null) {
//            mUserInformation = mGson.fromJson(json, UserInformation.class);
//        }
//    }
}
