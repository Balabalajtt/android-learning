package com.utte.hooktest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 */
public class ReflectUtils {

    /**
     * 反射获取对象
     */
    public static Object createObject(String className) {
        return createObject(className, new Class[]{}, new Object[]{});
    }

    public static Object createObject(Class clazz) {
        return createObject(clazz, new Class[]{}, new Object[]{});
    }

    public static Object createObject(String className, Class paraType, Object paraValue) {
        return createObject(className, new Class[]{paraType}, new Object[]{paraValue});

    }

    public static Object createObject(Class clazz, Class paraType, Object paraValue) {
        return createObject(clazz, new Class[]{paraType}, new Object[]{paraValue});
    }

    public static Object createObject(String className, Class[] paraTypes, Object[] paraValues) {
        try {
            Class c = Class.forName(className);
            return createObject(c, paraTypes, paraValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object createObject(Class clazz, Class[] paraTypes, Object[] paraValues) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor(paraTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(paraValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 反射调用实例方法
     */
    public static Object invokeInstanceMethod(Object obj, String methodName, Class[] paraTypes, Object[] paraValues) {
        if (obj == null) {
            return null;
        }
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, paraTypes);
            method.setAccessible(true);
            return method.invoke(obj, paraValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeInstanceMethod(Object obj, String methodName, Class paraType, Object paraVaule) {
        return invokeInstanceMethod(obj, methodName, new Class[]{paraType}, new Object[]{paraVaule});
    }

    public static Object invokeInstanceMethod(Object obj, String methodName) {
        return invokeInstanceMethod(obj, methodName, new Class[]{}, new Object[]{});
    }


    /**
     * 反射调用静态方法
     */
    public static Object invokeStaticMethod(String className, String methodName) {
        return invokeStaticMethod(className, methodName, new Class[]{}, new Object[]{});
    }

    public static Object invokeStaticMethod(Class clazz, String methodName) {
        return invokeStaticMethod(clazz, methodName, new Class[]{}, new Object[]{});
    }

    public static Object invokeStaticMethod(String className, String method_name, Class paraType, Object paraValue) {
        return invokeStaticMethod(className, method_name, new Class[]{paraType}, new Object[]{paraValue});
    }

    public static Object invokeStaticMethod(Class clazz, String methodName, Class paraType, Object paraValue) {
        return invokeStaticMethod(clazz, methodName, new Class[]{paraType}, new Object[]{paraValue});
    }

    public static Object invokeStaticMethod(String className, String method_name, Class[] paraTypes, Object[] paraVales) {
        try {
            Class c = Class.forName(className);
            return invokeStaticMethod(className, method_name, paraTypes, paraVales);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeStaticMethod(Class clazz, String method_name, Class[] paraTypes, Object[] paraValues) {
        try {
            Method method = clazz.getDeclaredMethod(method_name, paraTypes);
            method.setAccessible(true);
            return method.invoke(null, paraValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获取实例字段对象
     */
    public static Object getFieldObject(Object obj, String filedName) {
        return getFieldObject(obj.getClass(), obj, filedName);
    }

    public static Object getFieldObject(String className, Object obj, String filedName) {
        try {
            Class c = Class.forName(className);
            return getFieldObject(c, obj, filedName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(Class clazz, Object obj, String filedName) {
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 给对象字段赋值
     */
    public static void setFieldObject(Object obj, String filedName, Object filedValue) {
        setFieldObject(obj.getClass(), obj, filedName, filedValue);
    }

    public static void setFieldObject(String className, Object obj, String filedName, Object filedValue) {
        try {
            Class c = Class.forName(className);
            setFieldObject(c, obj, filedName, filedValue);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(Class clazz, Object obj, String filedName, Object filedValue) {
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, filedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 获取静态字段
     */
    public static Object getStaticFieldObject(String className, String filedName) {
        return getFieldObject(className, null, filedName);
    }

    public static Object getStaticFieldObject(Class clazz, String filedName) {
        return getFieldObject(clazz, null, filedName);
    }


    /**
     * 赋值静态字段
     */
    public static void setStaticFieldObject(String classname, String filedName, Object filedValue) {
        setFieldObject(classname, null, filedName, filedValue);
    }

    public static void setStaticFieldObject(Class clazz, String filedName, Object filedVaule) {
        setFieldObject(clazz, null, filedName, filedVaule);
    }



}
