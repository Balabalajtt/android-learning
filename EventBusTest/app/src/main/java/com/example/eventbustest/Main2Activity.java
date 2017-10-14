package com.example.eventbustest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button first = (Button) findViewById(R.id.first);
        Button second = (Button) findViewById(R.id.second);
        Button register = (Button) findViewById(R.id.register);
        Button unregister = (Button) findViewById(R.id.unregister);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent("first"));//发送事件
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent("second"));//发送事件
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(Main2Activity.this);
                }
            }
        });

        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().unregister(Main2Activity.this);
            }
        });

    }

    //粘性事件处理函数
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageSticky(MessageEvent messageEvent) {
        if (messageEvent.getMessage().length() < 3) {
            Toast.makeText(Main2Activity.this, messageEvent.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
