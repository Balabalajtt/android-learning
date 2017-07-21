package com.example.test07211;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button browserButton = (Button) findViewById(R.id.browser_button);
        browserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browsweIntent = new Intent(Intent.ACTION_VIEW);
                browsweIntent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(browsweIntent);
            }
        });
        Button telButton = (Button) findViewById(R.id.tel_button);
        telButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:15691761797"));
                startActivity(intent);
            }
        });
        Button dataButton = (Button) findViewById(R.id.data_button);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("key","回传一个数据");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("key","回传一个数据");
        setResult(RESULT_OK,intent);
        finish();
    }
}
