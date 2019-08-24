package com.hdh.baekalleyproject.ui.base.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseActivityContract.View {

    // Permission
    public static final int PERMISSION = 0x00;

    @Override
    public void removeActivity() {
        finish();
    }

    @Override
    public void moveActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * 권한 획득 결과
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION) {// 권한은 입력받은 순서대로 돌아옴
            // 0 : WRITE_EXTERNAL_STORAGE
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                } else {
                    // 권한 거부
                    finish();
                }
            }
        }
    }

    /**
     * 권한 획득
     */
    @Override
    public void isPermissionCheck() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            int callPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CALL_PHONE},
                        PERMISSION);
            }
        }
    }
}