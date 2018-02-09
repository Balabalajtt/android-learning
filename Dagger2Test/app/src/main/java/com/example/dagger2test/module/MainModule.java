package com.example.dagger2test.module;

import com.example.dagger2test.Cloth;
import com.example.dagger2test.Clothes;
import com.example.dagger2test.annotation.PerActivity;
import com.example.dagger2test.annotation.RedCloth;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 江婷婷 on 2018/2/8.
 */

@Module
public class MainModule {
    @Provides
    @RedCloth
    public Cloth getRedCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @PerActivity
    @Provides
    @Named("blue")
    public Cloth getBlueCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }

    @Provides
    public Clothes getClothes(@Named("blue") Cloth cloth) {
        return new Clothes(cloth);
    }

}
