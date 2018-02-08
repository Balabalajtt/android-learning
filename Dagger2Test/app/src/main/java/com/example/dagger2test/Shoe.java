package com.example.dagger2test;

import javax.inject.Inject;

/**
 * Created by 江婷婷 on 2018/2/8.
 */

public class Shoe {
    @Inject
    public Shoe() {
    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
