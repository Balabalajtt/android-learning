package com.example.videoplayertest;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
//import com.dl7.player.media.IjkPlayerView;

public class PlayerActivity extends AppCompatActivity {
//    private IjkPlayerView mPlayerView;
    private static final String IMAGE_URL = "http://img.kaiyanapp.com/044b497b10de3542eb6ab3794e45e0e3.jpeg?imageMogr2/quality/60/format/jpg";
    private static final String VIDEO_URL = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=14914&editionType=normal&source=ucloud";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_player);
//
//        mPlayerView = findViewById(R.id.player_view);
////        setSupportActionBar(mToolbar);
//        //  以下为配置接口，选择需要的调用
//        Glide.with(this).load(IMAGE_URL).fitCenter().into(mPlayerView.mPlayerThumb);    // 显示界面图
//        mPlayerView.init()              // 初始化，必须先调用
//                .setTitle("这是个标题")  // 设置标题，全屏时显示
//                .setSkipTip(1000*60*1)  // 设置跳转提示
//                .enableOrientation()    // 使能重力翻转
////                .setVideoPath(VIDEO_URL)    // 设置视频Url，单个视频源可用这个
//                .setVideoSource(null, VIDEO_URL, VIDEO_URL, "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=26630&editionType=normal&source=qcloud", null)    // 设置视频Url，多个视频源用这个
//                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)  // 指定初始视频源
////                .enableDanmaku()        // 使能弹幕功能
////                .setDanmakuSource(getResources().openRawResource(R.raw.comments))   // 添加弹幕资源，必须在enableDanmaku()后调用
//                .start();   // 启动播放

//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mPlayerView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mPlayerView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mPlayerView.onDestroy();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mPlayerView.configurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (mPlayerView.handleVolumeKey(keyCode)) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mPlayerView.onBackPressed()) {
//            return;
//        }
//        super.onBackPressed();
//    }
}
