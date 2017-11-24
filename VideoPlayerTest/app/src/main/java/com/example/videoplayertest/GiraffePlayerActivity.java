package com.example.videoplayertest;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

public class GiraffePlayerActivity extends AppCompatActivity {

    private static final String VIDEO_URL = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=14914&editionType=normal&source=ucloud";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giraffe_player);
        //全屏播放
//        GiraffePlayer.play(GiraffePlayerActivity.this, new VideoInfo(Uri.parse(VIDEO_URL)));

//        VideoInfo videoInfo = new VideoInfo(Uri.parse(VIDEO_URL))
//                .setTitle("test video");
//                .setBgColor(R.color.Grey_900); //config title
//                .setShowTopBar(true) //show mediacontroller top bar
//                .setPortraitWhenFullScreen(true);//portrait when full screen
//        GiraffePlayer.play(GiraffePlayerActivity.this, videoInfo);

        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoPath(VIDEO_URL).getPlayer().start();

    }
}
