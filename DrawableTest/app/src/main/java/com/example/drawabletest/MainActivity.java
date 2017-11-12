package com.example.drawabletest;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button b;
    private ImageView i;
    private TextView t;
    private View vs;
    private View vc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = findViewById(R.id.iv_level_list);
        b = findViewById(R.id.bt_level_list);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int l = i.getDrawable().getLevel();
                System.out.println(l);
                i.setImageLevel(l == 0 ? 1 : 0);
                b.getBackground().setLevel(l == 0 ? 1 : 0);
            }
        });
        t = findViewById(R.id.tv_translation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final TransitionDrawable td = (TransitionDrawable) t.getBackground();
                while (true) {
                    final long time = 3000;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            td.startTransition((int) time);
                        }
                    });
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            td.reverseTransition((int) time);
                        }
                    });
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        vs = findViewById(R.id.v_scale);
        vs.getBackground().setLevel(1000);
        vc = findViewById(R.id.v_clip);
        vc.getBackground().setLevel(8000);
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
            }
        });

    }
}
