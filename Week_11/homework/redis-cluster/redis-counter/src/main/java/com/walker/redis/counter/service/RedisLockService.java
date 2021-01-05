package com.walker.redis.counter.service;

/**
 * @author fcwalker
 * @date 2021/1/4 23:05
 **/
public interface RedisLockService {


    /**
     * 加锁
     * @param lockKey 加锁的Key
     * @param timeout 锁超时时间
     * @param tryTimeout 加锁过程的超时时间
     * @return
     */
    boolean lock(String lockKey, String timeout, Integer tryTimeout);

    /**
     * 解锁
     * @param lockKey
     * @return
     */
    boolean unlock(String lockKey);

    /**
     * 检测资源是否被锁
     * @param lockKey
     * @return
     */
    boolean isLocked(String lockKey);
}
