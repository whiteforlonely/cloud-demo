package com.ake.demo.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/28 20:00
 *
 * VM ARGS: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * 需要去target/classes目录下面去执行命令：  java -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError com.ake.demo.jvm.oom.HeapOOMDemo
 */
public class HeapOOMDemo {
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
