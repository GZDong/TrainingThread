package com.gzd.trainingthread;

import android.os.SystemClock;

public class ThreadsCommunicateTest {

    private void test() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i =0 ; i < 10000; i++) {
                    if (isInterrupted() && i > 5000) {
                        Thread.interrupted();//会恢复重置状态
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { //Exception不代表出错，是例外情况，意外路线
                        e.printStackTrace();   //也会重置
                    }
                    SystemClock.sleep(1000); //不会被打断
                }
            }
        };
        thread.stop(); //结果不可预期
        thread.interrupt();//标记为可结束，希望线程结束；然后让线程知道这个事情后自己决定怎么结束
    }
}
