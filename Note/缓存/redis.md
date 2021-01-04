# 安装

# 压测

![1609246328670](.\img\redis-benchmark.png)

# 基本数据机构

## 字符串(string) 

> 简单来说就三种：int、string、byte[]
>
> string在redis中是二进制安全的

**方法：**set/get/getset/del/exists/append

​            incr/decr/incrby/decrby

**注意：**

**字符串append：**会使用更多的内存，除非有连续的append，否则推荐用set来覆盖值。

**整数共享：**如果能使用整数，就尽量使用整数，但是如果我们限制了redis内存或者配置了LRU淘汰策略，都可能使得整数共享失效。

**精度问题：**redis大概能保证16左右的数据，17，18位往上的大整数会丢失精度。 

## 散列（hash）

> 可以看成是String key和String value的map容器。

**方法：**

## 列表（list）

> 可以看成是java的LinkedList

## 集合（set）

> 可以看成是Java里的set，不重复的list

redis 作为一个进程是多线程的，6之前的版本，每块都是单线程（多个模块）