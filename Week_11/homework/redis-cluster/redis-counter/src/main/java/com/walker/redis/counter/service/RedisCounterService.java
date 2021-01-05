package com.walker.redis.counter.service;

/**
 * @author fcwalker
 * @date 2021/1/5 22:05
 **/
public interface RedisCounterService {

    /**
     * 减库存
     * @param count
     * @return
     */
    boolean reduce(String spuCode, int count);

    /**
     * 增加库存
     * @param count
     * @return
     */
    boolean increase(String spuCode, int count);
}
