package com.example.videoplayertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tcking.github.com.giraffeplayer2.VideoView;
import static com.example.videoplayertest.Common.VIDEO_URL;

public class GiraffePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giraffe_player);

        //从xml里面加载出来的视频播放
        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoPath(VIDEO_URL).getPlayer().start();

    }
}
