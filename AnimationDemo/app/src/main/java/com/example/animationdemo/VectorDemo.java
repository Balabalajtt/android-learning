package com.example.animationdemo;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class VectorDemo extends AppCompatActivity {

    static {
        //带有状态的控件如果需要svg，除了要使用selector引用，还需要这个配置
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vevtor_demo);
    }

    public void anim(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable)drawable).start();
        }
    }

    public void switchToL(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(VectorDemo.this, MainLActivity.class));
        } else {
            Toast.makeText(VectorDemo.this, "系统版本不支持", Toast.LENGTH_SHORT).show();
        }
    }
}
