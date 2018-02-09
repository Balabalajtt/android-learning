package com.example.dagger2test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dagger2test.Cloth;
import com.example.dagger2test.ClothHandler;
import com.example.dagger2test.MyApplication;
import com.example.dagger2test.R;
import com.example.dagger2test.component.DaggerSecondComponent;
import com.example.dagger2test.component.SecondComponent;
import com.example.dagger2test.module.SecondModule;

import javax.inject.Inject;

public class SecondActivity extends AppCompatActivity {

    @Inject
    Cloth blueCloth;
    @Inject
    ClothHandler clothHandler;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv = findViewById(R.id.text2);

        SecondComponent secondComponent = DaggerSecondComponent
                .builder()
                .baseComponent(((MyApplication)getApplication()).getBaseComponent())
                .secondModule(new SecondModule())
                .build();
        secondComponent.inject(this);
        tv.setText(clothHandler.handler(blueCloth) + " " + clothHandler);

    }
}
