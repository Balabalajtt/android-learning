package com.example.dagger2test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by 江婷婷 on 2018/2/8.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}
