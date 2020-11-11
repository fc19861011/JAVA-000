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
 * 2、基于 newSingleThreadExecutor 实现
 * newSingleThreadExecutor：创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任
 * 务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务
 * 的执行顺序按照任务的提交顺序执行。
 *
 **/
public class SingleThreadExecutorRelease {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
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
