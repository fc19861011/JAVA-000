package com.walker.redis.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author fcwalker
 * @date 2021/1/5 22:06
 **/
@Service
public class RedisCounterServiceImpl implements RedisCounterService{

    @Autowired(required = false)
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisLockService redisLockService;

    private static final Long STORE_OUT = 0L;
    private static final Long NO_STORE = -1L;

    @Override
    public boolean reduce(String spuCode, int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("      if (redis.call('exists', KEYS[1]) == 1) then ")
                .append("   local stock = tonumber(redis.call('get', KEYS[1])); ")
                .append("   local num = tonumber(ARGV[1]); ")
                .append("   if (stock >= num) then ")
                .append("      return redis.call('decrby', KEYS[1], num); ")
                .append("   end; ")
                .append("   return 0; ")
                .append("end ")
                .append("return -1");
        String luaScript = sb.toString();
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(spuCode), String.valueOf(count));
        Long r = (Long) result;
        if (r > 0) {
            System.out.println(result + "：减库存成功：" + count);
            return true;
        } else if(NO_STORE.equals(r)) {
            System.err.println(result + "：没有库存");
            return false;
        } else {
            System.err.println(result + "：库存不足：" + count);
            return false;
        }
    }

    @Override
    public boolean increase(String spuCode, int count) {
        if(redisLockService.lock(spuCode+"_stock", "20000" , 10000)) {
            try {
                redisTemplate.opsForValue().increment(spuCode, count);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                redisLockService.unlock(spuCode+"_stock");
            }
            return true;
        }
        return false;
    }
}
