package com.walker.redis.counter;

import com.walker.redis.counter.service.RedisCounterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fcwalker
 * @date 2021/1/5 22:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisCounterApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisCounterTest {

    @Autowired
    RedisCounterService redisCounterService;

    private String spuCode = "pc";

    @Test
    public void increaseStock() {
        for(int i = 0; i < 20; i++) {
            new Thread(() -> redisCounterService.increase(spuCode, 10)).run();
        }
    }

    @Test
    public void reduceStock() {
        for(int i = 1; i < 21; i++) {
            int count = i % 3;
            new Thread(() -> redisCounterService.reduce(spuCode, count)).run();
        }
    }
}
