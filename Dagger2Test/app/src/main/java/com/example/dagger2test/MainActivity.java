package com.example.dagger2test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dagger2test.annotation.RedCloth;

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
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);

        //DaggerMainComponent要在Component编译之后才生成
        MainComponent mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        mainComponent.inject(this);
        textView.setText("我有" + redCloth + "和" + blueCloth + "和" + shoe + "和" + clothes
        + (blueCloth == clothes.getCloth()));

    }
}
