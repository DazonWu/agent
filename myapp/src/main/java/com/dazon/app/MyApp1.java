package com.dazon.app;

import com.dazon.common.Test1;

/**
 * @description:
 * @author: Dazon Wu
 * @date: 2021/11/3  11:47
 */
public class MyApp1 {


    public static void main(String[] args) {
        Test1 test1 = new Test1();


        System.out.println("main start");
        while (true){
            try {
                Thread.sleep(2000);
                System.out.println(test1.getNum()+"-----------"+test1.param);

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("main end");
                return;
            }
        }

    }


}
