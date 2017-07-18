package com.example.anln;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 1234;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent!=null)
        {
            String title = intent.getStringExtra(SplashActivity.TITLE);
            UserInfo userInfo = (UserInfo) intent.getSerializableExtra(SplashActivity.USER_INFO);
            setTitle(title + " lalala " + userInfo.getmUserName());
        }

        findViewById(R.id.button_first_activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button_second_activity_main).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"i'm clicked!",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_third_activity_main).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent1 = new Intent();
                intent1.putExtra(SplashActivity.TITLE,"来看我嘛");
                setResult(RESULT_CODE,intent1);
                finish();
            }
        });

        EditText editText2 = (EditText)findViewById(R.id.editText2);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 12)
                {
                    Toast.makeText(MainActivity.this,"超过12字啦！",Toast.LENGTH_SHORT).show();
                }

            }
        });


        findViewById(R.id.seekbar).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }
}

