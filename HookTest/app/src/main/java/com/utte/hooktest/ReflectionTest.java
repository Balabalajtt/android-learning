package com.utte.hooktest;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ReflectionTest {

    private static final String TAG = "ReflectionTest";

    /**
     * 获取Class对象的方法
     */
    public static void reflectClass() {
        // 1. 通过一个对象得到Class
        Class c1 = "aaa".getClass();

        // 2. 通过类名得到Class 需要全称类名
        Class c2 = null;
        try {
            c2 = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 3. 通过类的class属性
        Class c3 =  String.class;

        // 4. 基本类型通过TYPE属性
        Class c4 = Byte.TYPE;

    }

    /**
     * 获取构造函数并调用
     */
    public static void reflectConstructor() {
        /**
         * 获取所有构造函数
         */
        // 1. 获取类对象
        Class c = Test.class;
        // 2. 获取类名
        String name = c.getName();
        // 3. 获取构造方法数组，这个方法是获取所有的构造函数，包括private的
        // 如果只想获取public 方法，就用getConstructors()
        // Constructor[] cons = c.getConstructors();
        Constructor[] constructors = c.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            // 4. 获取构造方法的修饰域
            int mod = constructor.getModifiers();
            Log.d(TAG, Modifier.toString(mod) + " " + name + "( ");
            // 5. 获取构造方法的参数集合
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class parameterType : parameterTypes) {
                Log.d(TAG, parameterType.getName() + " ");
            }
            Log.d(TAG, ")");
        }

        /**
         * 获取特定某一个构造方法
         */
        Constructor con1 = null;
        Constructor con2 = null;
        Constructor con3 = null;
        try {
            // 获取那个无参构造方法
            con1 = c.getDeclaredConstructor();
            // 获取只有一个int型参数的构造方法
            Class[] p2 = {int.class};
            con2 = c.getDeclaredConstructor(p2);
            con2.setAccessible(true);
            // 获取两个参数的构造方法
            Class[] p3 = {String.class, int.class};
            con3 = c.getDeclaredConstructor(p3);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /**
         * 调用构造方法
         */
        Object obj1;
        Object obj11;
        Object obj2;
        Object obj3;
        try {
            obj1 = con1.newInstance();
            // 如果是无参构造方法，可以直接使用Class的newInstance()
            obj11 = c.newInstance();
            obj2 = con2.newInstance(2);
            obj3 = con3.newInstance("con3去实例对象", 3);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取实例方法并调用
     */
    public static void invokeMethod() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 获取类的Class对象
        Class c = Class.forName("com.utte.hooktest.Test");
        // 2. 获取对应的构造方法
        Constructor con = c.getDeclaredConstructor(String.class, int.class);
        // 3. 调用构造方法创建一个对象，用做方法的调用者
        Object obj = con.newInstance("Get Private Method", 1);
        // 4. 调用Class对象的getDeclaredMethod获取方法，第一个参数是方法名，后面是方法参数类型
        Method method = c.getDeclaredMethod("printNum", String.class);
        // 5. 修改访问域
        method.setAccessible(true);
        // 6. 调用Method对象的invoke()，调用该方法
        method.invoke(obj, "invoke this method");

    }

    /**
     * 获取静态方法并调用
     */
    public static void invokeStaticMethod() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 获取类的Class对象
        Class c = Class.forName("com.utte.hooktest.Test");
        // 2. 获取方法
        Method method = c.getDeclaredMethod("printStatic", String.class);
        // 3. 修改访问域
        method.setAccessible(true);
        // 4. 调用该方法
        method.invoke(null,"invoke this static method");

    }

    /**
     * 获取类的私有实例字段并修改它
     */
    public static void getPrivateField() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        // 1. 创建要被修改字段的实例
        Class c = Class.forName("com.utte.hooktest.Test");
        Constructor con = c.getDeclaredConstructor(String.class, int.class);
        Object obj = con.newInstance("Get Private Method", 1);
        // 2. 用Class对象和字段名获取对应Field对象
        Field field = c.getDeclaredField("priStr");
        field.setAccessible(true);
        // 3. 调用Field对象的set()去修改一个对象的该字段
        field.set(obj, "i hook this private String field!");
        // 4. 这个字段的修改只对obj这个对象有效
        Log.d(TAG, "getPrivateField: " + ((Test)obj).getPriStr());
    }

    /**
     * 获取类的私有静态字段并修改它
     */
    public static void getPrivateStaticField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 1. 创建要被修改字段的实例
        Class c = Class.forName("com.utte.hooktest.Test");
        // 2. 用Class对象和字段名获取对应Field对象
        Field field = c.getDeclaredField("priStaticStr");
        field.setAccessible(true);
        // 3. 调用Field对象的set()去修改一个对象的该字段
        field.set(null, "i hook this private static String field!");
        // 4. 因为是静态字段，所以这个字段的修改对什么都有效
        Log.d(TAG, "getPrivateField: " + new Test().getPriStaticStr());

    }



    public static void hookSingleton() throws ClassNotFoundException, NoSuchFieldException {
        Class c = Class.forName("android.util.Singleton");
        Field field = c.getDeclaredField("mInstance");
        field.setAccessible(true);


    }

    private void hookActivityManager() {
        // 1. 拿到ActivityManager#IActivityManagerSingleton静态对象
        Object singleton = ReflectUtils.getStaticFieldObject("android.app.ActivityManager", "IActivityManagerSingleton");
        // 2. 拿到IActivityManagerSingleton对象的mInstance字段
        Object instance = ReflectUtils.getFieldObject("android.util.Singleton", singleton, "mInstance");
        try {
            // 4. 代理对象需要实现的接口
            Class<?> inf = Class.forName("android.app.IActivityManager");
            // 5. 创建代理对象
            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{inf},
                    new HookInvocation(instance)
            );
            // 6. 用代理去替代IActivityManagerSingleton的mInstance字段
            ReflectUtils.setFieldObject("android.util.Singleton", singleton, "mInstance", proxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        try {
            HookStartActivity.hookActivityThread();
            Class<?> forName = null;
            forName = Class.forName("android.app.ActivityThread");

            Field field = forName.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            Object activityThread = field.get(null);
            Method getPackageManager = activityThread.getClass().getDeclaredMethod("getPackageManager");
            Object iPackageManager = getPackageManager.invoke(activityThread);
            PackageManagerHandler handler = new PackageManagerHandler(iPackageManager);
            Class<?> iPackageManagerIntercept = Class.forName("android.content.pm.IPackageManager");
            // 获取 sPackageManager 属性


            Object proxy = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iPackageManagerIntercept},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return null;
                        }
                    }
            );

//            Object proxy = Proxy.newProxyInstance(
//                    Thread.currentThread().getContextClassLoader(),
//                    new Class[]{inf},
//                    new HookActivityHandler(instance)
//            );

            Field iPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
            iPackageManagerField.setAccessible(true);
            iPackageManagerField.set(activityThread, proxy);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private class PackageManagerHandler implements InvocationHandler {
        public Object origin;
        public PackageManagerHandler(Object iPackageManager) {
            origin = iPackageManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(origin);
        }
    }

}

class HookInvocation implements InvocationHandler {
    private Object instance;
    private static final String TAG = "HookInvocation";

    HookInvocation(Object instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "im hooking Activity Manager");
        Log.d(TAG, "invoke: " + method.getName() + " " + Arrays.toString(args));
        return method.invoke(instance, args);
    }

}