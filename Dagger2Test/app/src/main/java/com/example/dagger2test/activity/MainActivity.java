package com.example.dagger2test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dagger2test.Cloth;
import com.example.dagger2test.ClothHandler;
import com.example.dagger2test.Clothes;
import com.example.dagger2test.MyApplication;
import com.example.dagger2test.R;
import com.example.dagger2test.Shoe;
import com.example.dagger2test.annotation.RedCloth;
import com.example.dagger2test.component.DaggerMainComponent;
import com.example.dagger2test.component.MainComponent;
import com.example.dagger2test.module.MainModule;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends AppCompatActivity {

    @Inject
    @RedCloth
    Cloth redCloth;
    @Inject
    @Named("blue")
    Cloth blueCloth;
    @Inject
    Clothes clothes;
    @Inject
    Shoe shoe;
    @Inject
    ClothHandler clothHandler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        //DaggerMainComponent要在Component编译之后才生成
//        MainComponent mainComponent = DaggerMainComponent
//                .builder()
//                .baseComponent(((MyApplication)getApplication()).getBaseComponent())
//                .mainModule(new MainModule())
//                .build();
        //使用SubComponent的方法
        MyApplication myApplication = (MyApplication)getApplication();
        myApplication.getBaseComponent()
                .getSubMainComponent(new MainModule())
                .inject(this);
//        mainComponent.inject(this);
        textView.setText(clothHandler.handler(redCloth) + " " + clothHandler);

    }
}
