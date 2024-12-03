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
     *
     *   这边如果是使用另外的一个垃圾回收器，对应的打印日志如下:
     *
     *    D:\Java\jdk1.8.0_291\bin\java -verbose:gc -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -Xmx20M com.ake.demo.jvm.gc.AllocationTest
     * 2024-12-03T20:47:40.186+0800: 0.060: [GC (Allocation Failure) [PSYoungGen: 5022K->480K(6144K)] 5022K->4824K(19968K), 0.0196165 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]
     * Heap
     *  PSYoungGen      total 6144K, used 2641K [0x00000000ff980000, 0x0000000100000000, 0x0000000100000000)
     *  // 新生代分配了6M的空间，使用了2M的空间，剩下的应该是都直接分配到老年代去了
     *   eden space 5632K, 38% used [0x00000000ff980000,0x00000000ffb9c410,0x00000000fff00000)
     *   // eden的空间为5M，survivor的空间为0.5M，
     *   from space 512K, 93% used [0x00000000fff00000,0x00000000fff78030,0x00000000fff80000)
     *   to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
     *  ParOldGen       total 13824K, used 8440K [0x00000000fec00000, 0x00000000ff980000, 0x00000000ff980000)
     *  // 老年代使用了13M的空间，用了8M的空间
     *   object space 13824K, 61% used [0x00000000fec00000,0x00000000ff43e040,0x00000000ff980000)
     *  Metaspace       used 2640K, capacity 4486K, committed 4864K, reserved 1056768K
     *  // 元空间为什么是使用了2M的大小，它到底存放的是什么东西呢，为什么是2M？ 元空间的可用内存是4M，可以用的空间也是4M，JVM可以给元空间分配的物理内存是1056768K
     *   class space    used 280K, capacity 386K, committed 512K, reserved 1048576K
     *   // 这个和元空间是类似的，类空间是存放类的静态内容和运行时常量池，
     * */
}
