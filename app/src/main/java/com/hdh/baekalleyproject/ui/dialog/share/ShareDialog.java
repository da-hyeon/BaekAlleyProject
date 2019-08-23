package com.hdh.baekalleyproject.ui.dialog.share;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShareDialog extends Dialog {

    private Context mContext;

    private View vKakao;
    private View vFaceBook;
    private View vURLCopy;
    private View vDismiss;

    private Restaurant mRestaurant;


    public ShareDialog(@NonNull Context context) {
        super(context);
        mContext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        //mBinding = DataBindingUtil.inflate(LayoutInflater.from(context) , R.layout.dialog_view_more , null , false);    //다이얼로그에서 사용할 레이아웃입니다.
        setContentView(R.layout.dialog_share);

        DisplayMetrics dm = context.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        WindowManager.LayoutParams wm = getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.height = dm.heightPixels / 2;

        initData();

        vKakao.setOnClickListener(v ->
                shareKakao()
        );

        vFaceBook.setOnClickListener(v ->
                {
                }
        );

        vURLCopy.setOnClickListener(v ->
                {
                }
        );

        vDismiss.setOnClickListener(v ->
                dismiss()
        );
    }

    private void initData() {

        vKakao = findViewById(R.id.vKakao);
        vFaceBook = findViewById(R.id.vFaceBook);
        vURLCopy = findViewById(R.id.vURLCopy);
        vDismiss = findViewById(R.id.vDismiss);

    }

    private void shareKakao() {

        //카카오톡 설치유무 체크
        if (!appInstalledOrNot("com.kakao.talk")) {
            Toast.makeText(mContext, "카카오톡을 설치합니다.", Toast.LENGTH_SHORT).show();
            mContext.startActivity(KakaoLinkService.getInstance().getKakaoTalkInstallIntent(mContext));
            dismiss();
            return;
        }

        String address = mRestaurant.getRestaurantAddress();
        String title = mRestaurant.getRestaurantName();
        String imageURL = mRestaurant.getRestaurantImageURL();

        LocationTemplate params = LocationTemplate.newBuilder(address,
                ContentObject.newBuilder(title,
                        imageURL,
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption("카카오 판교오피스 위치입니다.")
                        .build())
                .setAddressTitle(title)
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendDefault(mContext, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });

        dismiss();
    }

    /**
     * 앱 설치 유무 확인
     *
     * @param uri 패키지명
     * @return true - 설치 , false - 미설치
     */
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void setRestaurantData(Restaurant mRestaurant) {
        this.mRestaurant = mRestaurant;
    }
}
