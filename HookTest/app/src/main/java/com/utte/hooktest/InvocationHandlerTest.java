package com.utte.hooktest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerTest implements InvocationHandler {

    private Object mTarget;

    public InvocationHandlerTest(Object target) {
        mTarget = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始");
        Object o = method.invoke(mTarget, args);
        System.out.println("结束");
        return o;
    }


    public static void hook() {

    }

}
