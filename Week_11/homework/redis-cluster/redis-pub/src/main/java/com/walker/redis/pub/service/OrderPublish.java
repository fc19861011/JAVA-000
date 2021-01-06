package com.walker.redis.pub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fcwalker
 * @date 2021/1/6 23:22
 **/
@RestController
public class OrderPublish {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public final String CHANEL_NAME = "ordre_chanel";

    @GetMapping("/order/publish")
    public String orderPublish(String data) {
        redisTemplate.convertAndSend(CHANEL_NAME, data);
        return "success";
    }
}
