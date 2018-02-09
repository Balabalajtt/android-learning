package com.example.dagger2test.component;

import com.example.dagger2test.activity.SecondActivity;
import com.example.dagger2test.annotation.PerActivity;
import com.example.dagger2test.base.BaseComponent;
import com.example.dagger2test.module.SecondModule;

import dagger.Component;

/**
 * Created by 江婷婷 on 2018/2/9.
 */
@PerActivity
@Component(modules = SecondModule.class, dependencies = BaseComponent.class)
public interface SecondComponent {
    void inject(SecondActivity secondActivity);
}
