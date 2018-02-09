package com.example.dagger2test.component;

import com.example.dagger2test.activity.MainActivity;
import com.example.dagger2test.annotation.PerActivity;
import com.example.dagger2test.module.MainModule;

import dagger.Subcomponent;

/**
 * 子组件
 * Created by 江婷婷 on 2018/2/9.
 */
@PerActivity
@Subcomponent(modules = MainModule.class)
public interface SubMainComponent {
    void inject(MainActivity mainActivity);
}
