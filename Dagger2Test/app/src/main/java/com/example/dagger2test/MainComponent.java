package com.example.dagger2test;

import com.example.dagger2test.annotation.PerActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 江婷婷 on 2018/2/8.
 */
@PerActivity
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
