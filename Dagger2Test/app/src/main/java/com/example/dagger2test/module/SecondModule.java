package com.example.dagger2test.module;

import com.example.dagger2test.Cloth;
import com.example.dagger2test.annotation.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 江婷婷 on 2018/2/9.
 */
@Module
public class SecondModule {
    @PerActivity
    @Provides
    public Cloth getBlueCloth(){
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }
}
