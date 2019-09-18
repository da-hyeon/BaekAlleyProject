package com.hdh.baekalleyproject.ui.dialog.call;

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
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hdh.baekalleyproject.Constants;
import com.hdh.baekalleyproject.R;

import java.util.Objects;

public class CallDialog extends Dialog {

    private Context mContext;

    private TextView tvNumber;
    private Button btCall;
    private Button btCallCopy;
    private Button btDismiss;

    private String mCallNumber;


    public CallDialog(@NonNull Context context , String mCallNumber) {
        super(context);
        mContext = context;
        this.mCallNumber = mCallNumber;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_call);

        initData();

        tvNumber.setText(mCallNumber);

        btCall.setOnClickListener(v->
            clickCall()
        );

        btCallCopy.setOnClickListener(v->
            clickCallCopy()
        );

        btDismiss.setOnClickListener(v->{
            dismiss();
        });
    }

    private void initData() {
        tvNumber = findViewById(R.id.tvNumber);
        btCall = findViewById(R.id.btCall);
        btCallCopy = findViewById(R.id.btCallCopy);
        btDismiss = findViewById(R.id.btDismiss);
    }

    private void clickCall(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "010-7600-1318")));

        dismiss();
    }

    private void clickCallCopy(){
        //클립보드
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(Constants.RESTAURANT_ADDRESS, mCallNumber);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(mContext, "클립보드에 전화번호가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "전화번호 복사에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }

        dismiss();
    }
}
