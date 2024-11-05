package com.ake.demo.jvm.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/31 20:26
 */
public class PhantomReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        Object ref = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(ref, queue);

        // 试着垃圾回收
        System.gc();
        checkGC(queue);
        // 指控对象
        ref = null;
        System.gc();
        // 等待垃圾回收
        Thread.sleep(1000);

        checkGC(queue);
    }

    private static void checkGC(ReferenceQueue<Object> queue) {
        Reference<?> poll = queue.poll();
        if (poll != null) {
            System.out.println("gc occur");
        } else {
            System.out.println("gc not occur");
        }

    }
}
