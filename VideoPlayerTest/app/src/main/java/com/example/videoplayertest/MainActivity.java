package com.example.videoplayertest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;

import static com.example.videoplayertest.Common.VIDEO_URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.bt);
        bt.setOnClickListener(this);

        Button bt2 = findViewById(R.id.bt2);
        bt2.setOnClickListener(this);

        Button bt3 = findViewById(R.id.bt3);
        bt3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                Intent intent = new Intent(MainActivity.this, GiraffePlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.bt2:
                GiraffePlayer.play(MainActivity.this, new VideoInfo(Uri.parse(VIDEO_URL)));
                break;
            case R.id.bt3:
                //可以自定义一些更多的样式再传进去播放
                VideoInfo videoInfo = new VideoInfo(Uri.parse(VIDEO_URL))
                        .setTitle("test video")
                        .setBgColor(getResources().getColor(R.color.colorPrimaryDark)) //config title
                        .setShowTopBar(true) //show mediacontroller top bar
                        .setPortraitWhenFullScreen(true);//portrait when full screen
                GiraffePlayer.play(MainActivity.this, videoInfo);
        }
    }
}
