package com.example.dagger2test;

import android.app.Application;

import com.example.dagger2test.base.BaseComponent;
import com.example.dagger2test.base.BaseModule;
import com.example.dagger2test.base.DaggerBaseComponent;

/**
 * Created by 江婷婷 on 2018/2/9.
 */

public class MyApplication extends Application {
    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        baseComponent = DaggerBaseComponent.builder().baseModule(new BaseModule()).build();
    }

    public BaseComponent getBaseComponent() {
        return baseComponent;
    }
}
