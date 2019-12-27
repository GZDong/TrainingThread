package com.gzd.trainingthread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.app.Service;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import java.util.concurrent.Executor;

public class AndroidThreadActivity extends AppCompatActivity {

    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_thread);

        final Handler handler = new Handler();   //安卓特有的线程交互，一个线程让另一个线程做事情
        new Thread() {
            @Override
            public void run() {
                //耗时操作。。。
                handler.post(new Runnable() {  //进入messageQueue
                    @Override
                    public void run() {
                        //UI操作
                    }
                });
            }
        };
        handler.post(new Runnable() {  //往looper的主线程扔东西
            @Override
            public void run() {

            }
        });
        Handler handler1 = new Handler() {  //其实是统一的进入主线程的操作
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
        handler1.dispatchMessage(new Message());//其实也是post，简化版，方便版去发送消息、通知，
        ThreadLocal<Integer> threadLocal;   //每个线程都有的东西，不共享数据
        //线程x线程y同时操作threadLocal，没用的！各自操作threadLocal
        android.os.Looper looper = android.os.Looper.myLooper(); //每个线程里调用就可以获得自己的looper


        new MyAsyncTask().execute(?, ?, ?);

        Thread thread;
        thread.setDaemon(true);//守护进程：守护进程的进程；守护线程：可以被杀掉，其实和Android关系不大

        Executor; //有任务后台扔；能用优先用
        AsyncTask;Handler; //后台做任务，还需要交互给前台的场景
        HandlerThread;//做一个持续帮其他线程做事的线程；实际上场景少；记得回收
        IntentService;//可以有上下文的后台服务；本质也只是个线程

        Service;//后台任务管理活动空间，本身没有后台能力
        IntentService;//单个任务；可以带上下文，有上下文：发广播，启动线程，活动。。。。。
    }

    class HandlerThread extends Thread {
        Looper looper = new Looper();//实际上，使用Looper的prepare的方法创建

        @Override
        public void run() {
            looper.loop();
        }
    }

    class Looper {
        private Runnable task;
        private volatile boolean quit;
        MessageQueue messageQueue;//Message或者Runnable

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        void loop() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                    }
                }
            }
        }

        void trueLoop() {
            for (;;) {
                if (messageQueue.next == null) {
                    return;
                }
            }
        }
    }

    class MyAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }

    //java回收：没有指针指向你就可以回收; GC Root
    //GCRoot：运行中的线程、静态对象、来自本地代码的引用指定的对象，不被回收
    //运行中的线程导致的内存泄露不需要处理，意义不大
}
