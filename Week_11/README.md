# 作业一
## 基于 Redis 封装分布式数据操作：
- [x] 在 Java 中实现一个简单的分布式锁；
```
# 加锁lua脚本：
if redis.call('setNx',KEYS[1],ARGV[1]) then   if redis.call('get',KEYS[1])==ARGV[1] then     return redis.call('expire',KEYS[1],ARGV[2])   else      return 0   end end
# 解锁lua脚本
if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end
```
- [x] 在 Java 中实现一个分布式计数器，模拟减库存。

# 作业二
## 基于 Redis 的 PubSub 实现订单异步处理