package com.utte.tinkertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.utte.tinkertest.tinker.module.TinkerManager;
import com.utte.tinkertest.tinker.module.TinkerService;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

//    private static final String FILE_END = ".apk";
//    private String mPatchDir;
private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAllService();
    }

    private void startAllService() {
//         启动TinkerService
        Intent intent = new Intent(this, TinkerService.class);
        startService(intent);
    }

    public void loadPatch(View view) {
    }



}
