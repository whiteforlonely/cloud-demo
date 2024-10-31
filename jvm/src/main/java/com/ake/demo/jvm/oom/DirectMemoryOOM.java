package com.ake.demo.jvm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/30 20:49
 *
 * VM Args: -XX:MaxDirectMemorySize=10m
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        try {
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            while (true) {
                unsafe.allocateMemory(_1MB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
