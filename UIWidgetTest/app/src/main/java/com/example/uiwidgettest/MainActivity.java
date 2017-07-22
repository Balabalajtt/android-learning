package com.example.uiwidgettest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEditText;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private String mInput;
    private int mCount;
    private Button mHintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mHintButton = (Button) findViewById(R.id.hint_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInput = mEditText.getText().toString();
                if(mProgressBar.getVisibility()==View.GONE && (mInput.equals("1")|| mInput.equals("2")|| mInput.equals("3")))
                {
                    //按钮点击后隐藏键盘
                    InputMethodManager inputMethodManager =(InputMethodManager)MainActivity.this.getApplicationContext().
                            getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

                    Toast.makeText(MainActivity.this, "your level is " + mInput + " START!", Toast.LENGTH_SHORT).show();

                    mHintButton.setVisibility(View.VISIBLE);

                    if(mProgressBar.getVisibility()==View.GONE)
                    {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    if(mInput.equals("1"))
                    {
                        mCount = 10;
                    }
                    if(mInput.equals("2"))
                    {
                        mCount = 5;
                    }
                    if(mInput.equals("3"))
                    {
                        mCount = 1;
                    }

                    mButton.setText("Hang On");

                }
                else if(mButton.getText().equals("Start") ||mButton.getText().equals("Restart"))
                {
                    AlertDialog.Builder alerDialog = new AlertDialog.Builder(MainActivity.this);
                    if (mInput.equals(""))
                    {
                        alerDialog.setTitle("请设置等级");

                        alerDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                    }
                    else
                    {
                        alerDialog.setTitle("等级设置有误");
                        alerDialog.setMessage("等级设置1-3，1为最简单。");

                        alerDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                    }
                    alerDialog.show();


                    //Toast.makeText(MainActivity.this, "error input", Toast.LENGTH_SHORT).show();
                }



            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mImageView.setImageResource(R.drawable.banma);

                int progress = mProgressBar.getProgress();
                progress += mCount;
                mProgressBar.setProgress(progress);

                if(mProgressBar.getVisibility()!=View.GONE)
                {
                    if(mHintButton.getVisibility()==View.VISIBLE)
                    {
                        mHintButton.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        mHintButton.setVisibility(View.VISIBLE);
                    }
                    if(mProgressBar.getProgress()==100)
                    {
                        //Toast.makeText(MainActivity.this,"You Win!",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setMessage("你赢啦！\n是否继续游戏？");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mInput="";
                                mButton.setText("Restart");
                                mProgressBar.setProgress(0);
                                mImageView.setImageResource(R.drawable.banma);
                                mProgressBar.setVisibility(View.GONE);
                                mHintButton.setVisibility(View.INVISIBLE);
                            }
                        });
                        alertDialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        alertDialog.show();
                    }
                    if(mProgressBar.getProgress()>70)
                    {
                        mImageView.setImageResource(R.drawable.chicken);
                    }
                }
            }
        });

    }
}
