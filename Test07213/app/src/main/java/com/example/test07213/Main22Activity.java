package com.example.test07213;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main22Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Log.d("Main22Activity", "Task id is"+getTaskId());
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAll();
                //android.os.Process.killProcess(android.os.Process.myPid());//kill process
                //System.exit(0);
            }
        });
    }

    public static void actionStart(Context context, String s1,String s2)
    {
        Intent intent = new Intent(context,Main22Activity.class);
        intent.putExtra("data1",s1);
        intent.putExtra("data2",s2);
        context.startActivity(intent);
        Log.d("Main22Activity", "传过来的数据"+s1+s2);
    }

}
