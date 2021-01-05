package com.walker.redis.counter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fcwalker
 * @date 2021/1/4 22:45
 **/
@SpringBootApplication
public class RedisCounterApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisCounterApp.class, args);
    }
}
