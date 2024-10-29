package com.ake.demo.jvm.oom;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/29 19:51
 *
 * VM Args: -Xss128K
 */
public class StackOverFlowDemo {

    private int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) {
        StackOverFlowDemo stackOverFlowDemo = new StackOverFlowDemo();
        try {
            stackOverFlowDemo.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + stackOverFlowDemo.stackLength);
            e.printStackTrace();
        }
    }
}
