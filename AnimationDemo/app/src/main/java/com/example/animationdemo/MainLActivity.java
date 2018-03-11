package com.example.animationdemo;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_l);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animL(View view) {
        ImageView imageView = (ImageView) view;
        //5.0以上必须手动转换成 AnimatedVectorDrawable
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.star_anim);
        imageView.setImageDrawable(drawable);
        if (drawable != null) {
            drawable.start();
        }
    }
}
