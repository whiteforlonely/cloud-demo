package com.ake.demo.jvm.oom;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/29 20:16
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1); //true JDK7及以上，说明常量池是在堆中
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2); // false intern()拿到的是之前的引用（’java‘之前已经存在），而builder创建的是一个新的字符串
    }
}
