package com.utte.wifip2ptest.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.utte.wifip2ptest.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 申请权限
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请文件读写权限
        requirePermission();
    }

    public void sendFile(View v) {
        startActivity(new Intent(this, SendFileActivity.class));
    }

    public void receiveFile(View v) {
        startActivity(new Intent(this, ReceiveFileActivity.class));
    }

    @AfterPermissionGranted(1000)
    private void requirePermission() {
        String[] perms = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要文件读取权限",
                    1000, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        Log.e(TAG, "权限申成功");
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        Log.e(TAG, "权限申请失败");
    }
}
