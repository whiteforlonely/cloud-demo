package com.ake.demo.jvm.gc;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/2 20:24
 */
public class AllocationTest {

    private static final int _1MB = 1024 * 1024;

    public static void testAllocation()
    {
        byte[] a1, a2, a3, a4;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }

    /**
     * D:\Java\jdk1.8.0_291\bin\java -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 com.ake.demo.jvm.gc.AllocationTest
     * 下面指向的都是虚拟内存空间
     * Heap
     *  PSYoungGen      total 9216K, used 7292K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *  // 年轻代分了了6M的空间，Survivor暂时都没有存放
     *   eden space 8192K, 89% used [0x00000000ff600000,0x00000000ffd1f0f8,0x00000000ffe00000)
     *   from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     *   to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     *  ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *  // 有4M的数据直接已到了老年代，因为已经放不下了。
     *   object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
     *  Metaspace       used 2640K, capacity 4486K, committed 4864K, reserved 1056768K
     *   class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
     * */
}
