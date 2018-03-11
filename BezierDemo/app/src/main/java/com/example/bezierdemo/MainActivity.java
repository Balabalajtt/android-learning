package com.example.bezierdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secondBezier(View view) {
        startActivity(new Intent(MainActivity.this, SecondBezierActivity.class));
    }

    public void thirdBezier(View view) {
        startActivity(new Intent(MainActivity.this, ThirdBezierActivity.class));
    }

    public void drawPadBezier(View view) {
        startActivity(new Intent(MainActivity.this, DrawPadActivity.class));
    }

    public void pathMorthingAnimation(View view) {
        startActivity(new Intent(MainActivity.this, PathMorphingActivity.class));
    }

    public void waveBezier(View view) {
        startActivity(new Intent(MainActivity.this, WaveActivity.class));
    }

    public void pathBezier(View view) {
        startActivity(new Intent(MainActivity.this, PathBezierActivity.class));
    }

}
