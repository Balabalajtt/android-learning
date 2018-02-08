package com.example.dagger2test;

/**
 * Created by 江婷婷 on 2018/2/8.
 */

public class ClothHandler {
    public Clothes handler(Cloth cloth) {
        return new Clothes(cloth);
    }
}
