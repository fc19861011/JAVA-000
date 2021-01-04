package com.walker.redis.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fcwalker
 * @date 2021/1/4 22:45
 **/
@SpringBootApplication
public class RedisLockApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisLockApp.class, args);
    }
}
