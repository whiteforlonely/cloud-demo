package com.ake.demo.jvm.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/30 19:53
 * java -XX:PermSize=10M -XX:MaxPermSize=10M com.ake.demo.jvm.oom.MethodAreaOOM
 * JDK17的时候，没有办法在进行上面的虚拟机参数的设置，会提示错误：
 * Unrecognized VM option 'PermSize=10M'
 * Error: Could not create the Java Virtual Machine.
 * Error: A fatal exception has occurred. Program will exit.
 *
 * 使用了-XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M来控制，但是好像并没有什么效果，
 * 堆内存没有明显的增加，反而是对外内存占用空间不断地上涨，还是不明白其中的缘由啊。
 */
public class MethodAreaOOM {

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invoke(obj, args1));
            enhancer.create();
        }
    }
}
