package com.walker.redis.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author fcwalker
 * @date 2021/1/4 23:05
 **/
@Service
public class RedisLockServiceImpl implements RedisLockService {

    @Autowired(required = false)
    RedisTemplate<String, String> redisTemplate;

    private static final Long SUCCESS = 1L;

    @Override
    public boolean lock(String lockKey, String timeout, Integer tryTimeout) {
        if(isLocked(lockKey)) {
            System.out.println("资源锁定中");
            return false;
        }
        // 获取当前时间
        long nanoTime = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        // 双重检查
        sb.append("      if (redis.call('exists', KEYS[1]) == 0) then ")
                .append("   if redis.call('setNx',KEYS[1],ARGV[1]) then ")
                .append("      if redis.call('get',KEYS[1])==ARGV[1] then")
                .append("         return redis.call('expire',KEYS[1],ARGV[2]) ")
                .append("      else ")
                .append("         return 0 ")
                .append("      end ")
                .append("   end ")
                .append("   return 0 ")
                .append("end ")
                .append("return 0");
        String luaScript = sb.toString();
        System.out.println(luaScript);

        do {
            RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockKey, timeout);
            if (SUCCESS.equals(result)) {
                return true;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((System.nanoTime() - nanoTime) < TimeUnit.MILLISECONDS.toNanos(tryTimeout));
        return false;
    }

    @Override
    public boolean unlock(String lockKey) {
        String luaScript = "if redis.call('get',KEYS[1])==ARGV[1] then " +
                "              return redis.call('del',KEYS[1]) " +
                "           else " +
                "              return 0 " +
                "           end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockKey);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isLocked(String lockKey) {
        return redisTemplate.hasKey(lockKey);
    }
}
