package com.example.anln;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by 江婷婷 on 2017/7/17.
 */

public class SplashActivity extends Activity{

    public static final String TITLE = "title";
    public static final String USER_INFO = "userInfo";
    public static final int REQUEST_CODE = 98;
    private TextView mTextView;
    private static final String TAG = SplashActivity.class.getSimpleName();
    Handler mHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mTextView = (TextView) findViewById(R.id.title_text_view);
        final String title = mTextView.getText().toString();

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
        },1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"requestcode:" + requestCode + ",resultcode:" + requestCode);

        if(requestCode == REQUEST_CODE && resultCode == MainActivity.RESULT_CODE)
        {
            if(data != null)
            {
                String title = data.getStringExtra(TITLE);
                mTextView.setText(title);
            }

        }
    }
}