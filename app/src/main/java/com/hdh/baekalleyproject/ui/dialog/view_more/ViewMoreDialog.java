package com.hdh.baekalleyproject.ui.dialog.view_more;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.R;
import com.hdh.baekalleyproject.data.model.Restaurant;
import com.hdh.baekalleyproject.ui.review_write.ReviewWriteActivity;

import java.util.Objects;

public class ViewMoreDialog extends Dialog {

    private Context mContext;

    private ImageView ivNumberOfLike;
    private TextView tvNumberOfLike;
    private LinearLayout llNumberOfLike;

    private View vNumberOfLikeButton;
    private View vWriteReview;
    private View vCall;
    private View vAddressCopy;
    private ConstraintLayout vDismiss;

    private Restaurant mRestaurant;

    public ViewMoreDialog(@NonNull Context context) {
        super(context);
        mContext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        //mBinding = DataBindingUtil.inflate(LayoutInflater.from(context) , R.layout.dialog_view_more , null , false);    //다이얼로그에서 사용할 레이아웃입니다.
        setContentView(R.layout.dialog_view_more);

        DisplayMetrics dm = context.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        WindowManager.LayoutParams wm = getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = dm.widthPixels;
        getWindow().setGravity(Gravity.BOTTOM);

        initData();


        vNumberOfLikeButton.setOnClickListener(v ->
                changeNumberOfLikeColor(!(tvNumberOfLike.getCurrentTextColor() == ContextCompat.getColor(context, R.color.colorPrimary)))
        );

        vDismiss.setOnClickListener(v ->
                dismiss()
        );

        vWriteReview.setOnClickListener(v ->
                clickWriteReview()
        );

        vCall.setOnClickListener(v ->
                clickCall()
        );

        vAddressCopy.setOnClickListener(v ->
                clickAddressCopy()
        );

    }

    private void initData() {
        ivNumberOfLike = findViewById(R.id.ivNumberOfLike);
        tvNumberOfLike = findViewById(R.id.tvNumberOfLike);
        llNumberOfLike = findViewById(R.id.llNumberOfLike);

        vNumberOfLikeButton = findViewById(R.id.vNumberOfLikeButton);
        vWriteReview = findViewById(R.id.vWriteReview);
        vCall = findViewById(R.id.vCall);
        vAddressCopy = findViewById(R.id.vAddressCopy);
        vDismiss = findViewById(R.id.clBottom);
    }

    private void changeNumberOfLikeColor(boolean status) {
        if (status) {
            ivNumberOfLike.setImageResource(R.drawable.icon_likeit_on);
            tvNumberOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            ivNumberOfLike.setImageResource(R.drawable.icon_likeit);
            tvNumberOfLike.setTextColor(ContextCompat.getColor(mContext, R.color.goTextDefaultColor));
        }
    }

    /**
     * 리뷰작성 클릭 이벤트 처리
     */
    private void clickWriteReview() {
        Intent intent = new Intent(mContext, ReviewWriteActivity.class);
        intent.putExtra(Constants.RESTAURANT_ID, Integer.parseInt(mRestaurant.getRestaurantID()));
        mContext.startActivity(intent);

        dismiss();
    }

    /**
     * 전화하기 클릭 이벤트 처리
     */
    private void clickCall() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "010-7600-1318")));
        dismiss();
    }

    /**
     * 주소복사 클릭 이벤트 처리
     */
    private void clickAddressCopy(){
        //클립보드
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(Constants.RESTAURANT_ADDRESS ,mRestaurant.getRestaurantAddress());
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(mContext, "클립보드에 주소가 복사되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "주소 복사에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    public void setRestaurantData(Restaurant mRestaurant) {
        this.mRestaurant = mRestaurant;
    }
}
