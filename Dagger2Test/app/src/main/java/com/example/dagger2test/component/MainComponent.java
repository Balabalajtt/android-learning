package com.example.dagger2test.component;

import com.example.dagger2test.activity.MainActivity;
import com.example.dagger2test.annotation.PerActivity;
import com.example.dagger2test.base.BaseComponent;
import com.example.dagger2test.module.MainModule;

import dagger.Component;

/**
 * Created by 江婷婷 on 2018/2/8.
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = BaseComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
