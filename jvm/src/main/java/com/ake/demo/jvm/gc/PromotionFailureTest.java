package com.ake.demo.jvm.gc;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/6 20:00
 *
 * D:\Java\jdk1.8.0_291\bin/java -verbose:gc -Xms20M -Xms20M -Xmn10M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -XX:-HandlePromotionFailure \com.ake.demo.jvm.gc.PromotionFailureTest
 * Unrecognized VM option 'HandlePromotionFailure'
 * Did you mean '(+/-)PromotionFailureALot'?
 * Error: Could not create the Java Virtual Machine.
 * Error: A fatal exception has occurred. Program will exit.
 *
 * here I can not test again now, because the -XX:HandlePromotionFailure has missed in jdk8
 *
 * unfortunately the oldest version of my jkd is jdk8
 */
public class PromotionFailureTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];

        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }
}
