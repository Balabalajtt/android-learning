package com.example.anln;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by 江婷婷 on 2017/7/17.
 */

public class SplashActivity extends Activity{

    public static final String TITLE = "title";
    public static final String USER_INFO = "userInfo";
    public static final int REQUEST_CODE = 98;
    Handler mHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView textView = (TextView) findViewById(R.id.title_text_view);
        final String title = textView.getText().toString();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到MainActivity
                UserInfo userInfo = new UserInfo("江婷婷",18);
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra(TITLE,title);
                intent.putExtra(USER_INFO,userInfo);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        },2000);

    }


}