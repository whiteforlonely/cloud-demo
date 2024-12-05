package com.ake.demo.jvm.gc;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/5 20:29
 * D:\Java\jdk1.8.0_291\bin\javac .\com\ake\demo\jvm\gc\TenuringThresholdTest2.java
 * D:\Java\jdk1.8.0_291\bin\java -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+UseConcMarkSweepGC -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:MaxTenuringThreshold=15 com.ake.demo.jvm.gc.TenuringThresholdTest2
 * 2024-12-05T20:41:20.068+0800: 0.060: [GC (Allocation Failure) 2024-12-05T20:41:20.078+0800: 0.070: [ParNew: 5592K->1024K(9216K), 0.0051081 secs] 5592K->5320K(19456K), 0.0150865 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * 2024-12-05T20:41:20.083+0800: 0.075: [GC (Allocation Failure) 2024-12-05T20:41:20.083+0800: 0.075: [ParNew: 5120K->0K(9216K), 0.0012447 secs] 9416K->5261K(19456K), 0.0013800 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 2024-12-05T20:41:20.085+0800: 0.077: [GC (CMS Initial Mark) [1 CMS-initial-mark: 5261K(10240K)] 9357K(19456K), 0.0002705 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * 2024-12-05T20:41:20.085+0800: 0.077: [CMS-concurrent-mark-start]
 * Heap
 *  par new generation   total 9216K, used 4260K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *   eden space 8192K,  52% used [0x00000000fec00000, 0x00000000ff0290e0, 0x00000000ff400000)
 *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
 *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
 *  concurrent mark-sweep generation total 10240K, used 5261K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  Metaspace       used 2640K, capacity 4486K, committed 4864K, reserved 1056768K
 *   class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
 * 2024-12-05T20:41:20.086+0800: 0.078: [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 */
public class TenuringThresholdTest2 {

    private static final int _1MB = 1024 * 1024;

    private static void testTenuringThreshold()
    {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testTenuringThreshold();
    }
}
