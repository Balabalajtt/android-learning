package com.example.mvptest.net;

import com.example.mvptest.LoadTasksCallBack;

/**
 * Created by 江婷婷 on 2017/11/6.
 */
public interface NetTask<T> {
    void execute(T data, LoadTasksCallBack callBack);
}
