package com.ake.demo.jvm.jartool;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/23 19:39
 */
public class JHSDBTest {

    private static class ObjectHolder {}

    static class Test {
        static ObjectHolder staticObj =new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }
    }
    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
