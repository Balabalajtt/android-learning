package com.example.dagger2test.base;

import com.example.dagger2test.ClothHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 江婷婷 on 2018/2/8.
 */
@Module
public class BaseModule {
    @Singleton
    @Provides
    public ClothHandler getClothHandler() {
        return new ClothHandler();
    }
}
