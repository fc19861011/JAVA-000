package com.power.runable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author F.C 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池， 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *         写出你的方法，越多越好，提交到github。
 *
 *         2、基于 ThreadPoolExecutor 实现 ThreadPoolExecutor： CPU密集型核心线程数一般为核心数*2 IO密集型核心线程数一般为核心数*4
 *
 **/
public class ThreadPoolExecutorRelease {
    /**
     * 启动一个main函数 默认会有2个active线程： Thread[main,5,main] 以及 Thread[Monitor Ctrl-Break,5,main]
     */
    private static final int THREAD_ACTIVE_COUNT = 2;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        // 在这里创建一个线程或线程池

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, keepAliveTime,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
        // 异步执行
        AtomicInteger result = new AtomicInteger();
        executor.submit(()->{result.set(sumByCache(36));});
        executor.shutdown();
        // 通过当前活动线程数，判断线程池是否结束
        // main方法调用，默认会有 Thread[main,5,main] 以及 Thread[Monitor Ctrl-Break,5,main]
        while (Thread.activeCount() > THREAD_ACTIVE_COUNT) {
            // 等待线程执行结束
        }
        // 确保 拿到result 并输出
        System.out.println("[主线程]，异步计算结果为：" + result);
        System.out.println("[主线程]，使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        System.out.println("[主线程]，main finished!");
    }

    private static int sumByCache(int num) {
        // return fibo(36);
        int[] cacheNumAry = new int[num + 1];
        for (int i = 0; i <= num; i++) {
            if (i < 2) {
                cacheNumAry[i] = 1;
            } else {
                cacheNumAry[i] = cacheNumAry[i - 1] + cacheNumAry[i - 2];
            }
        }
        return cacheNumAry[num];
    }

    private static int sumByTailCall(int num) {
        return fibo(num);
    }

    /**
     * 递归的问题： 普通递归的问题在于展开的时候会产生非常大的中间缓存， 而每一层的中间缓存都会占用我们宝贵的栈上空间， 所有导致了当这个 n 很大的时候，栈上空间不足则会产生“爆栈”的情况
     *
     * 尾递归（Tail call）： 若函数在尾位置调用自身（或是一个尾调用本身的其他函数等等）， 则称这种情况为尾递归。尾递归也是递归的一种特殊情形。
     * 尾递归是一种特殊的尾调用，即在尾部直接调用自身的递归函数。 对尾递归的优化也是关注尾调用的主要原因。 尾调用不一定是递归调用，但是尾递归特别有用，也比较容易实现。
     *
     * 特点： 尾递归在普通尾调用的基础上，多出了2个特征： 1. 在尾部调用的是函数自身 (Self-called)； 2. 可通过优化，使得计算仅占用常量栈空间 (Stack Space)。
     *
     * 优化： 尾调用由于是函数的最后一步操作，所以不需要保留外层函数的调用记录， 因为调用位置、内部变量等信息都不会再用到了， 只要直接用内层函数的调用记录，取代外层函数的调用记录就可以了。
     * 
     * @param a
     * @return
     */
    private static int fibo(int a) {
        if (a < 2) return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;
        private final boolean daemon;

        public NamedThreadFactory(String namePrefix, boolean daemon) {
            this.daemon = daemon;
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public NamedThreadFactory(String namePrefix) {
            this(namePrefix, false);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
            t.setDaemon(daemon);
            System.out.println("[线程工厂]，线程工厂创建了一个线程");
            return t;
        }
    }

}
