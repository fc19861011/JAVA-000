package com.walker.redis.lock;

import com.walker.redis.lock.service.RedisLockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fcwalker
 * @date 2021/1/4 23:38
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisLockApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisLockTest {
    @Autowired
    RedisLockService redisLockService;

    @Test
    public void testLock() {
        String key = "lockKey";
        System.out.println("加锁开始");
        System.out.println("判断是否存在锁："+redisLockService.isLocked(key));
        System.out.println("加锁："+redisLockService.lock(key, "60000", 10000));
        System.out.println("判断是否存在锁："+redisLockService.isLocked(key));
        System.out.println("加锁："+redisLockService.lock(key, "60000", 10000));
        System.out.println("解锁："+redisLockService.unlock(key));
        System.out.println("判断是否存在锁："+redisLockService.isLocked(key));
    }
}
