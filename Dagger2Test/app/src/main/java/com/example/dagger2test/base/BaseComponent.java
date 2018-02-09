package com.example.dagger2test.base;

import com.example.dagger2test.ClothHandler;
import com.example.dagger2test.component.SubMainComponent;
import com.example.dagger2test.module.MainModule;

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

    //@Subcomponent使用的声明方式,声明一个返回值为子组件的方法,子组件需要什么Module,就在方法参数中添加什么
    SubMainComponent getSubMainComponent(MainModule mainModule);
}
