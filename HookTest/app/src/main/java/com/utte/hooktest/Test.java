package com.utte.hooktest;

import android.util.Log;

public class Test {
    String name;
    int num;
    private static final String TAG = "Test";

    private String priStr = "im a private field!";
    private static String priStaticStr = "im a private static field!";

    public Test() {
        Log.d(TAG, "Test: " + "无参构造方法");
    }

    public Test(String name, int num) {
        this.name = name;
        this.num = num;
        Log.d(TAG, "Test: " + "string int 构造方法");
    }

    private Test(int num) {
        this.num = num;
        Log.d(TAG, "Test: " + "int 构造方法");
    }

    private Test(String name) {
        this.name = name;
        Log.d(TAG, "Test: " + "string 构造方法");
    }

    private void printNum(String msg) {
        Log.d(TAG, "printNum: " + num + ", msg: " + msg);
    }

    private static void printStatic(String msg) {
        Log.d(TAG, "printStatic: " + "I'm a static method!" + "msg: " + msg);
    }

    public String getPriStr() {
        return priStr;
    }

    public String getPriStaticStr() {
        return priStaticStr;
    }
}
