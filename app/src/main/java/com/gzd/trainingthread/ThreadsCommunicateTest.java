package com.gzd.trainingthread;

import android.os.SystemClock;
import android.util.Log;

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

    private synchronized void test1() {
        //...执行完，可以通知阻塞线程进入队列
        notify(); //唤醒一个
        notifyAll();//唤醒全部
    }
    private synchronized void test2() {
        //...假如必须是在test1之后才能执行，但是因为synchronized的原因，如果这里卡住等待test1，那么test1会永远拿不到
        //使用wait，进入阻塞区，不进入队列，将资源释放
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //wait和notify，记住不是写在线程本身里面，而是资源附近，让执行到这里的线程wait，或者唤醒等待这个操作完成的线程去排队
    //wait和notify是monitor的方法，而monitor可以是任何Object，所以这2个方法其实是Object的方法，所以
    // 配合synchronized才有意义，因为那种情况下才会有monitor

    private void test3() {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("tset","run: " );
            }
        };
        Thread.yield();//同优先级线程，让出下一个时间片
    }
}
