package com.gzd.trainingthread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedLockResTest {

    private int y;

    /**
     * 2个线程访问时，内部属性会有问题
     */
    public void test1() {
        int x = 0;  //原子操作
        y++;
    }


    /**
     * 有性能问题
     * 本质是对数据资源的同步控制，不是对方法
     * 会先访问monitor
     */
    public synchronized void test() {

    }
    //test和test2只要有一个被访问，另一个方法也会被锁住
    public synchronized void test2() {}
    public void test3() {} //如果不加synchronized，可以访问，有出错的风险，如果内部操作的属性相关

    //synchronized 直接写的时候monitor为当前对象，也可以指定，设置不同对象为monitor，这样就可以访问test2了
    public void test4() {
        synchronized (this) {

        }
    }

    Object object = new Object();
    public void test5() {
        synchronized (object) {

        }
    }
    //其实线程访问资源：
    //1.拷贝一份一样的
    //2.操作完放回去
    //这样性能高
    //而synchronized，其实是让每个线程继续执行前去拿最新值
    //乐观锁和悲观锁，是对于数据库的读取写入
    //乐观锁：读不加锁，写之前判断是否和取之前相同，不同加锁，同直接写
    //悲观锁：读就加锁，写后解锁
    static synchronized void test6() {} //monitor为对象，和synchronized(Test.class)一样
    volatile SynchronizedLockResTest test; // 让test的初始化有同步性，对除double和float赋值之外，
    // 对引用有效，可以理解为synchronized的简单版
    // 比如双锁单例，可能初始化没完成，但是对象不为空
    AtomicInteger count = new AtomicInteger(0);//对基本类型，使用这种轻量级api
    Lock lock = new ReentrantLock();
    private void test8() {
        lock.lock();
        //...  如果报错，将死不解锁，所以一般用try finally
        lock.unlock();
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }
    //读读线程，是没问题的
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock read = readWriteLock.readLock();//给读的地方加
    Lock write = readWriteLock.writeLock();//给写的地方加
    //线程安全的本质：读写，写读，写写问题对共享资源的访问
}
