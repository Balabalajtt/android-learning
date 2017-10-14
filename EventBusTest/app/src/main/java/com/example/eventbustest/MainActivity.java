package com.example.eventbustest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tiaozhuan;
    private Button sticky;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);//注册事件

        tiaozhuan = (Button) findViewById(R.id.tiaozhuan);
        tiaozhuan.setOnClickListener(this);
        sticky = (Button) findViewById(R.id.sticky);
        sticky.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tiaozhuan:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.sticky:
                count++;
                EventBus.getDefault().postSticky(new MessageEvent(count + ""));//发送粘性事件
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消订阅
    }

    //不管事件发布在哪个线程，处理都会在UI线程执行，可以用来更新UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent messageEvent) {
        String s = messageEvent.getMessage();
        if (s.equals("first") || s.equals("second")) {
            tiaozhuan.setText(s);
        }
    }

    //事件在哪个线程发布，处理就会在哪个线程执行
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPosing(MessageEvent messageEvent) {

    }

    //如果事件在UI线程发布，处理就在新线程中执行
    //如果在子线程发布，处理时间就在发布事件的线程执行
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent messageEvent) {

    }

    //无论在哪个线程发布，都会在改线程的新建子线程中执行
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventASTNC(MessageEvent messageEvent) {

    }

}
