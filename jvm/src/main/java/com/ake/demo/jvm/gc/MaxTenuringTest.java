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
    /**
     * D:\Java\jdk1.8.0_291\bin\java -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=5000000 -XX:MaxTenuringThreshold=15 -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps com.ake.demo.jvm.gc.MaxTenuringTest
     * 2024-12-04T20:54:21.300+0800: 0.058: [GC (Allocation Failure) 2024-12-04T20:54:21.300+0800: 0.058: [ParNew: 5336K->1000K(9216K), 0.0007394 secs] 5336K->1000K(19456K), 0.0010566 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * 2024-12-04T20:54:21.302+0800: 0.059: [GC (Allocation Failure) 2024-12-04T20:54:21.302+0800: 0.059: [ParNew: 5096K->0K(9216K), 0.0016333 secs] 5096K->908K(19456K), 0.0018030 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * 2024-12-04T20:54:21.304+0800: 0.061: [GC (Allocation Failure) 2024-12-04T20:54:21.304+0800: 0.062: [ParNew: 4096K->0K(9216K), 0.0022478 secs] 5004K->5004K(19456K), 0.0024138 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * 2024-12-04T20:54:21.307+0800: 0.064: [GC (Allocation Failure) 2024-12-04T20:54:21.307+0800: 0.064: [ParNew: 4096K->0K(9216K), 0.0039182 secs] 9100K->9100K(19456K), 0.0040699 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * 2024-12-04T20:54:21.311+0800: 0.068: [GC (CMS Initial Mark) [1 CMS-initial-mark: 9100K(10240K)] 13196K(19456K), 0.0003450 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * 2024-12-04T20:54:21.311+0800: 0.069: [CMS-concurrent-mark-start]
     * Heap
     *  par new generation   total 9216K, used 4260K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   eden space 8192K,  52% used [0x00000000fec00000, 0x00000000ff0290e0, 0x00000000ff400000)
     *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     *  concurrent mark-sweep generation total 10240K, used 9100K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *  Metaspace       used 2640K, capacity 4486K, committed 4864K, reserved 1056768K
     *   class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
     * 2024-12-04T20:54:21.312+0800: 0.070: [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     *
     * here if set -XX:MaxTenuringThreshold=15,the the result of survivor will surprise you, if you just only know that
     * all the objects will promote to old generation at the age of the setting arguments, then this is not right!
     *
     * there are n objects in survivor have the same ages, and also the total size of these objects are large or equal to the
     * half size of survivor, then all of these objects will promote to old generation directly!
     */
}
