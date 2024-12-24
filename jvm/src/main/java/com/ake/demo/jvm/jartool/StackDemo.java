package com.ake.demo.jvm.jartool;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/23 17:20
 */
public class StackDemo {

    public static void main(String[] args) {
        // 打印出程序的堆栈信息
        Thread.getAllStackTraces().forEach((k, v) -> {
            System.out.println(k.getName());
            for (StackTraceElement stackTraceElement : v) {
                System.out.println(stackTraceElement);
            }
        });
    }
}
