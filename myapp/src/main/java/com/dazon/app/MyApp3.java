package com.dazon.app;

/**
 * @description:
 * @author: Dazon Wu
 * @date: 2021/11/3  14:07
 */
public class MyApp3 {

    public static void main(String[] args) {
        System.out.println("main33333 start");
        while (extracted()){
            /*if (extracted()){
                return;
            }*/
        }
    }

    private static boolean extracted() {
        try {
            Thread.sleep(1000);
            System.out.println("main33333 running...");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("main33333 end");
            return false;
        }
        return true;
    }
}
