package com.power.guava;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author F.C 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池， 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 *         写出你的方法，越多越好，提交到github。
 *
 *         基于 guava 实现
 *
 **/
public class GuavaRelease {
    /**
     * 启动一个main函数 默认会有2个active线程： Thread[main,5,main] 以及 Thread[Monitor Ctrl-Break,5,main]
     */
    private static final int THREAD_ACTIVE_COUNT = 2;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        final ListeningExecutorService service =
                MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        AtomicInteger result = new AtomicInteger();

        final ListenableFuture<Integer> sumTask = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return sumByCache(36);
            }
        });

        // 避免使用.get()方法阻塞主线程
        Futures.addCallback(sumTask, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 确保 拿到result 并输出
                System.out.println("[guava回调]，计算结果为：" + result);
                System.out.println("[guava回调]，使用时间：" + (System.currentTimeMillis() - start) + " ms");
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        service.shutdown();
        System.out.println("[主线程]，使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 等待主线程结束
        while(Thread.activeCount() > THREAD_ACTIVE_COUNT) {
            // 等待线程执行结束
        }
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
