package com.example.uiwidgettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInput = mEditText.getText().toString();
                if(mProgressBar.getVisibility()==View.GONE && (mInput.equals("1")|| mInput.equals("2")|| mInput.equals("3")))
                {
                    Toast.makeText(MainActivity.this, "your level is " + mInput + " START!", Toast.LENGTH_SHORT).show();
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
                else
                {
                    Toast.makeText(MainActivity.this, "error input", Toast.LENGTH_SHORT).show();
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
                    if(mProgressBar.getProgress()==100)
                    {
                        Toast.makeText(MainActivity.this,"You Win!",Toast.LENGTH_SHORT).show();
                        mButton.setText("Restart");
                        mProgressBar.setProgress(0);
                        mImageView.setImageResource(R.drawable.banma);
                        mProgressBar.setVisibility(View.GONE);
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
