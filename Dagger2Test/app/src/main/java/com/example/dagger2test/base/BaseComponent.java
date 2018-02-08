package com.example.dagger2test.base;

import com.example.dagger2test.ClothHandler;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 江婷婷 on 2018/2/8.
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {
    //BaseComponent是给其他Component提供依赖的,所以不用inject方法
    ClothHandler getClothHandler();
}
