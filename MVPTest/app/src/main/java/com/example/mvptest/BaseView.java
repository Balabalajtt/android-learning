package com.example.mvptest;

/**
 * Created by 江婷婷 on 2017/11/6.
 * 给view绑定presenter
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
