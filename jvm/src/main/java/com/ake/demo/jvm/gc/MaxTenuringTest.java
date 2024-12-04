package com.ake.demo.jvm.gc;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/4 20:35
 *
 * java -verbose:gc -Xms20M -Xmx20m -Xmn20M -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1 -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps com.ake.demo.jvm.gc.MaxTenuringTest
 */
public class MaxTenuringTest {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB/4];
        allocation2 = new byte[4 * _1MB];
        allocation2 = null;
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];   // here send allocation1 to old generation
        allocation1 = new byte[4 * _1MB];
    }
}
