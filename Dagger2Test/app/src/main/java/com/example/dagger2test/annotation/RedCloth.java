package com.example.dagger2test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 自定义注解 可以当成@Name("...")使用
 * Created by 江婷婷 on 2018/2/8.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface RedCloth {}
