package com.power.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author F.C
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池， 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 2、基于 newCachedThreadPool 实现
 * newCachedThreadPool：创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
 * 那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添
 * 加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者
 * 说JVM）能够创建的最大线程大小。
 *
 * callable注意点：
 * 线程池调用callable的线程时，需要使用submit方法
 * 调用callable的线程池如果不调用.get(),则异步处理，否则会阻塞主线程
 * 多个callable的线程执行时即使调用.get()方法，callable线程之间也是并行执行的
 *
 **/
public class CachedThreadPoolRelease {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        final ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行
        int result = 0;
        try {
            result = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(10000);
                    return sumByCache(36);
                }
            }).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
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

}
