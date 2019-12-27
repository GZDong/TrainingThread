package com.gzd.trainingthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进程不共享资源，程序
        //线程共享资源，运行在程序。做完结束
        //cpu线程，8核，同时做8件事
        //系统线程，时间片
        //UI线程是一个无限循环线程，每一次循环圈都是一次ui刷新
        Thread thread = new Thread() {  //可以重用线程
            @Override
            public void run() {

            }
        };
        thread.start();
        new Thread(new Runnable() { //不用创建对象
            @Override
            public void run() {

            }
        }).start();
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "test");
            }
        };
//        Thread thread1 = factory.newThread();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Executor executor = Executors.newCachedThreadPool();
        ExecutorService executors = Executors.newFixedThreadPool(20);//适合批量处理
        executors.shutdown(); //记得关掉
        executor.execute(runnable);
        BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(1000);
        new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, queue);
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            future.get();//会阻塞
            while (!future.isDone()) {
                //...做点其他事情
                future.get();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
