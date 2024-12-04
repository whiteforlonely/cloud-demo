package com.ake.demo.jvm.gc;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/4 19:51
 * jvm args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 *
 * order: D:\Java\jdk1.8.0_291\bin\java -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728 com.ake.demo.jvm.gc.PretenureSizeThresholdTest
 */
public class PretenureSizeThresholdTest {

    private static final int _1MB = 1024 * 1024;


    public static void main(String[] args) {
        /*
         * -XX:PretenureSizeThreshold=1048576
         * Heap
         *  par new generation   total 9216K, used 1148K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   eden space 8192K,  14% used [0x00000000fec00000, 0x00000000fed1f0d0, 0x00000000ff400000)
         *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
         *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
         *  concurrent mark-sweep generation total 10240K, used 4096K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *  Metaspace       used 2641K, capacity 4486K, committed 4864K, reserved 1056768K
         *   class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
         *
         *  here set -XX:PretenureSizeThreshold=3145728
         *  but the log print -XX:PretenureSizeThreshold=1048576，why? I am sure this is not type error!
         *  so perhaps -XX:PretenureSizeThreshold=1048576 is the default value, but I have set this argument,
         * why it does not work?
         *
         * so here I need to check out if the value of default is work now, I need to allocate 2MB memory object
         */

        // -XX:PretenureSizeThreshold=3145728 is what I print!!!! so ~~~~~, speechless!
        System.out.println("-XX:PretenureSizeThreshold=" + _1MB);
        byte[] allocation;
        allocation = new byte[3 * _1MB];

        /*
        * here I need to make another test, user parnew gc, and find out if this argument will work or not!、
        * the log is:
        * Heap
 par new generation   total 9216K, used 3196K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  eden space 8192K,  39% used [0x00000000fec00000, 0x00000000fef1f0f8, 0x00000000ff400000)
  from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
  to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
   the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
 Metaspace       used 2642K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
===========================
* this means it cannot work in parnew GC.
        */
    }

}
